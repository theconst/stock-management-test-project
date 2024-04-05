package com.eclub.service;

import com.eclub.mapper.SaleItemEntityToSaleItemMapper;
import com.eclub.mapper.SaleItemToSaleItemEntityMapper;
import com.eclub.model.SaleItem;
import com.eclub.repository.CustomerRepository;
import com.eclub.repository.SaleItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class SaleServiceImpl implements SaleService {
    private final SaleItemRepository saleItemRepository;
    private final CustomerRepository customerRepository;
    private final SaleItemToSaleItemEntityMapper saleItemToSaleItemEntityMapper;
    private final SaleItemEntityToSaleItemMapper saleItemEntityToSaleItemMapper;

    //TODO: wrap value

    @Override
    @Transactional
    public Mono<SaleItem> recordSale(SaleItem saleItem) {
        return saleItemRepository
                .save(saleItemToSaleItemEntityMapper.map(saleItem))
                .map(saleItemEntityToSaleItemMapper::map);
    }
}
