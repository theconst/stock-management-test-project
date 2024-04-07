package com.eclub.service;

import com.eclub.domain.RemoveFromStockOperationId;
import com.eclub.domain.SaleItem;
import com.eclub.entity.RemoveFromStockEntity;
import com.eclub.entity.SaleItemEntity;
import com.eclub.mapper.SaleItemToRemoveFromStockEntityMapper;
import com.eclub.mapper.SaleItemToSaleItemEntityMapper;
import com.eclub.repository.RemoveFromStockRepository;
import com.eclub.repository.SaleItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class SaleServiceImpl implements SaleService {
    private final SaleItemRepository saleItemRepository;
    private final RemoveFromStockRepository removeFromStockRepository;

    private final SaleItemToSaleItemEntityMapper saleItemToSaleItemEntityMapper;
    private final SaleItemToRemoveFromStockEntityMapper saleItemToRemoveFromStockEntityMapper;

    @Override
    @Transactional
    public Mono<RemoveFromStockOperationId> recordSale(SaleItem saleItem) {
        var operationId = UUID.randomUUID().toString();

        return Mono.zip(saleItemRepository.save(saleItemToSaleItemEntityMapper.map(saleItem)),
                        removeFromStockRepository.save(saleItemToRemoveFromStockEntityMapper.map(saleItem, operationId)))
                .map(i -> new RemoveFromStockOperationId(operationId));
    }
}
