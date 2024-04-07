package com.eclub.service;

import com.eclub.domain.AddToStock;
import com.eclub.domain.RemoveFromStock;
import com.eclub.domain.StockItem;
import com.eclub.domain.StockItem.StockItemId;
import com.eclub.domain.StockOperation;
import com.eclub.entity.StockItemEntity;
import com.eclub.entity.StockOperationEntity;
import com.eclub.mapper.*;
import com.eclub.repository.ProductRepository;
import com.eclub.repository.StockOperationRepository;
import com.eclub.repository.StockRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static com.eclub.util.PagingCollector.collectPages;

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
class StockServiceImpl implements StockService {
    private final ProductRepository productRepository;
    private final StockRepository stockRepository;
    private final StockOperationRepository stockOperationRepository;

    private final ProductIdMapper productIdMapper;
    private final StockItemIdMapper stockItemIdMapper;
    private final BatchNumberMapper batchNumberMapper;
    private final StockItemEntityToStockItemMapper stockItemEntityToStockItemMapper;
    private final AddToStockToStockItemMapper addToStockToAddToStockItemMapper;
    private final OperationIdMapper operationIdMapper;

    @Override
    @Transactional
    public Mono<StockItem> getStockItem(StockItemId stockItemId) {
        return stockRepository
                .findById(stockItemId.id())
                .flatMap(this::assembleStockItem);
    }

    @Override
    @Transactional
    public Mono<StockItem> update(StockOperation stockOperation) {
        return stockOperationRepository.findById(operationIdMapper.map(stockOperation.operationId()))
                .flatMap(operation -> {

                    log.info("Operation with id [{}] already processed", operation.getOperationId());

                    return getStockItem(stockOperation)
                            .switchIfEmpty(itemUnavailableError(operation));
                })
                .switchIfEmpty(Mono.defer(() -> processOperation(stockOperation)))
                .flatMap(this::assembleStockItem);
    }

    @Override
    public Mono<Boolean> isOperationProcessed(StockOperation.OperationId id) {
        return stockOperationRepository.existsById(operationIdMapper.map(id));
    }

    private Mono<StockItemEntity> processOperation(StockOperation stockOperation) {
        var operationId = operationIdMapper.map(stockOperation.operationId());

        log.info("Processing operation with id [{}]", operationId);

        return Mono.zip(
                stockOperationRepository.save(StockOperationEntity.of(operationId, ZonedDateTime.now(ZoneId.of("UTC")))),
                getStockItem(stockOperation)
                        .doOnNext(stockItemEntity -> doUpdateStock(stockItemEntity, stockOperation))
                        .flatMap(stockRepository::save)
                        .switchIfEmpty(tryCreateStockItem(stockOperation)))
                .map(Tuple2::getT2);
    }

    private static Mono<StockItemEntity> itemUnavailableError(StockOperationEntity operation) {
        return Mono.error(new IllegalStateException("Stock item for %s already unavailable".formatted(operation)));
    }

    private Mono<StockItemEntity> tryCreateStockItem(StockOperation stockOperation) {
        return stockOperation.match(
                this::createNewStockItem,
                remove -> Mono.error(new IllegalStateException(
                        "Cannot sale non existing stock item [%s]".formatted(stockOperation))));
    }

    private static void doUpdateStock(StockItemEntity stockItemEntity, StockOperation stockOperation) {
        int inStock = stockItemEntity.getQuantity();
        int newQuantity = stockOperation.match(
                add -> inStock + add.quantity(),
                remove -> subtractFromStock(inStock, remove.quantity()));

        stockItemEntity.setQuantity(newQuantity);
    }

    private Mono<StockItemEntity> getStockItem(StockOperation stockOperation) {
        return stockOperation.match(this::getStockItem, this::getStockItem);
    }

    private Mono<StockItemEntity> getStockItem(RemoveFromStock removeFromStock) {
        return stockRepository.findById(stockItemIdMapper.map(removeFromStock.stockItemId()));
    }

    private Mono<StockItemEntity> getStockItem(AddToStock addToStock) {
        return stockRepository.findByBatchNumberAndProductId(
                batchNumberMapper.map(addToStock.batchNumber()),
                productIdMapper.map(addToStock.productId()));
    }

    private static int subtractFromStock(int inStock, int quantity) {
        int result = inStock - quantity;
        if (result < 0) {
            throw new IllegalArgumentException(
                    "Stock exhausted. Attempting to sell %s items, but only %s in stock"
                            .formatted(quantity, inStock));
        }
        return result;
    }

    private Mono<StockItemEntity> createNewStockItem(AddToStock addToStock) {
        return stockRepository.save(addToStockToAddToStockItemMapper.map(addToStock));
    }

    @Override
    public Mono<Page<StockItem>> listStock(PageRequest pageRequest) {
        return stockRepository
                .findAllByOrderByStockItemId(pageRequest)
                .flatMap(this::assembleStockItem)
                .transform(collectPages(pageRequest))
                .single();
    }

    private Mono<StockItem> assembleStockItem(StockItemEntity stockItem) {
        return productRepository
                .findById(stockItem.getProductId())
                .map(product -> stockItemEntityToStockItemMapper.map(stockItem, product));
    }
}
