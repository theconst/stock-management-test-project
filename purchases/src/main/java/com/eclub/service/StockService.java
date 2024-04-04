package com.eclub.service;

import com.eclub.domain.Purchase;
import com.eclub.domain.StockItem;
import com.eclub.domain.StockItem.StockItemId;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface StockService {
    Mono<StockItem> getStockItem(StockItemId stockItemId);

    Mono<StockItem> purchase(Purchase purchase);

    Flux<StockItem> listStock();
}
