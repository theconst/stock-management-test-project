package com.eclub.service;

import com.eclub.domain.SaleItem;
import com.eclub.domain.SaleRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import reactor.core.publisher.Mono;

public interface SaleService {

    Mono<SaleRecord> recordSale(SaleItem saleItem);

    Mono<SaleItem> findSaleById(SaleItem.SaleItemId id);

    Mono<Page<SaleItem>> listSales(PageRequest pageRequest);
}
