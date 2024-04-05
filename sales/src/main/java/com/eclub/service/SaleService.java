package com.eclub.service;

import com.eclub.model.SaleItem;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

public interface SaleService {
    @Transactional
    Mono<SaleItem> recordSale(SaleItem saleItem);
}
