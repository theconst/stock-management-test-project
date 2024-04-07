package com.eclub.service;

import com.eclub.domain.StockItem;
import com.eclub.domain.StockItem.StockItemId;
import com.eclub.domain.StockOperation;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface StockService {
    Mono<StockItem> getStockItem(StockItemId stockItemId);

    Mono<StockItem> update(StockOperation purchase);

    Flux<StockItem> listStock();
}
