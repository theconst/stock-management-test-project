package com.eclub.service;

import com.eclub.domain.SaleItem;
import com.eclub.domain.SaleItemAndStockOperationId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import reactor.core.publisher.Mono;

public interface SaleService {

    Mono<SaleItemAndStockOperationId> recordSale(SaleItem saleItem);

    Mono<SaleItem> findSaleById(SaleItem.SaleItemId id);

    Mono<Page<SaleItem>> listSales(PageRequest pageRequest);
}
