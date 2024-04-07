package com.eclub.service;

import com.eclub.domain.RemoveFromStockOperationId;
import com.eclub.domain.SaleItem;
import com.eclub.domain.SaleItem.SaleItemId;
import com.eclub.domain.SaleRecord;
import com.eclub.mapper.SaleItemEntityToSaleItemMapper;
import com.eclub.mapper.SaleItemToRemoveFromStockEntityMapper;
import com.eclub.mapper.SaleItemToSaleItemEntityMapper;
import com.eclub.repository.RemoveFromStockRepository;
import com.eclub.repository.SaleItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static com.eclub.util.PagingCollector.collectPages;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class SaleServiceImpl implements SaleService {
    private final SaleItemRepository saleItemRepository;
    private final RemoveFromStockRepository removeFromStockRepository;

    private final SaleItemToSaleItemEntityMapper saleItemToSaleItemEntityMapper;
    private final SaleItemToRemoveFromStockEntityMapper saleItemToRemoveFromStockEntityMapper;
    private final SaleItemEntityToSaleItemMapper saleItemEntityToSaleItemMapper;

    @Override
    @Transactional
    public Mono<SaleRecord> recordSale(SaleItem saleItem) {
        var operationId = UUID.randomUUID().toString();

        return Mono.zip(saleItemRepository.save(saleItemToSaleItemEntityMapper.map(saleItem)),
                        removeFromStockRepository.save(saleItemToRemoveFromStockEntityMapper.map(saleItem, operationId)))
                .map(saleItemAndOperationId ->
                        new SaleRecord(
                                saleItemEntityToSaleItemMapper.map(saleItemAndOperationId.getT1()),
                                new RemoveFromStockOperationId(saleItemAndOperationId.getT2().getOperationId())));
    }

    @Override
    public Mono<SaleItem> findSaleById(SaleItemId id) {
        return saleItemRepository
                .findById(id.id())
                .map(saleItemEntityToSaleItemMapper::map);
    }

    @Override
    public Mono<Page<SaleItem>> listSales(PageRequest pageRequest) {
        return saleItemRepository.findAllByOrderBySaleId(pageRequest)
                .map(saleItemEntityToSaleItemMapper::map)
                .transform(collectPages(pageRequest, saleItemRepository::count))
                .single();
    }
}
