package com.eclub.service;

import com.eclub.domain.RemoveFromStockOperationId;
import com.eclub.domain.SaleItem;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

public interface SaleService {
    Mono<RemoveFromStockOperationId> recordSale(SaleItem saleItem);
}
