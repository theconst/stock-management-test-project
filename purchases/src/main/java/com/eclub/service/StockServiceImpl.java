package com.eclub.service;

import com.eclub.domain.Purchase;
import com.eclub.domain.StockItem;
import com.eclub.domain.StockItem.StockItemId;
import com.eclub.domain.StockOperation;
import com.eclub.entity.StockItemEntity;
import com.eclub.mapper.BatchNumberMapper;
import com.eclub.mapper.ProductIdMapper;
import com.eclub.mapper.PurchaseToStockItemEntityMapper;
import com.eclub.mapper.StockItemEntityToStockItemMapper;
import com.eclub.repository.ProductRepository;
import com.eclub.repository.StockRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
class StockServiceImpl implements StockService {
    private final ProductRepository productRepository;
    private final StockRepository stockRepository;

    private final ProductIdMapper productIdMapper;
    private final BatchNumberMapper batchNumberMapper;
    private final StockItemEntityToStockItemMapper stockItemEntityToStockItemMapper;
    private final PurchaseToStockItemEntityMapper purchaseToStockItemEntityMapper;

    @Override
    @Transactional
    public Mono<StockItem> getStockItem(StockItemId stockItemId) {
        return stockRepository
                .findById(stockItemId.id())
                .flatMap(this::assembleStockItem);
    }

    @Override
    @Transactional                                                        //TODO(kkovalchuk): verify indeed transaction
    public Mono<StockItem> update(StockOperation stockOperation) {
        var batchNumber = batchNumberMapper.map(stockOperation.batchNumber());
        var productId = productIdMapper.map(stockOperation.productId());

        return stockRepository
                .findByBatchNumberAndProductId(batchNumber, productId)
                .doOnNext(stockItemEntity -> {
                    int inStock = stockItemEntity.getQuantity();
                    int newQuantity = stockOperation.match(
                            purchase -> inStock + purchase.quantity(),
                            sale -> subtractFromStock(inStock, sale.quantity()));

                    //TODO(kkovalchuk): try immutable entities
                    stockItemEntity.setQuantity(newQuantity);
                })
                .flatMap(stockRepository::save)
                .switchIfEmpty(stockOperation.match(
                        this::createNewStockItem,
                        sale -> Mono.error(new IllegalStateException(
                                "Cannot sale non existing stock item [%s]".formatted(stockOperation)))))
                .flatMap(this::assembleStockItem);
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

    private Mono<StockItemEntity> createNewStockItem(Purchase purchase) {
        return stockRepository.save(purchaseToStockItemEntityMapper.map(purchase));
    }

    @Override
    public Flux<StockItem> listStock() {
        return stockRepository
                .findAll()
                .flatMap(this::assembleStockItem);
    }

    private Mono<StockItem> assembleStockItem(StockItemEntity stockItem) {
        return productRepository
                .findById(stockItem.getProductId())
                .map(product -> stockItemEntityToStockItemMapper.map(stockItem, product));
    }
}
