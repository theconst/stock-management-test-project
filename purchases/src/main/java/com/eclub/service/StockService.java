package com.eclub.service;

import com.eclub.domain.StockItem;
import com.eclub.domain.StockItem.StockItemId;
import com.eclub.domain.StockOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import reactor.core.publisher.Mono;

public interface StockService {
    Mono<StockItem> getStockItem(StockItemId stockItemId);

    Mono<StockItem> update(StockOperation purchase);

    Mono<Boolean> isOperationProcessed(StockOperation.OperationId id);

    Mono<Page<StockItem>> listStock(PageRequest pageRequest);
}
