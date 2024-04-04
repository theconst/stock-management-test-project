package com.eclub.service;

import com.eclub.domain.Price;
import com.eclub.domain.Product.ProductId;
import com.eclub.domain.StockItem;
import reactor.core.publisher.Mono;

public interface StockService {

    Mono<StockItem> addToStock(ProductId productId, Price sellingPrice, int quantity);
}
