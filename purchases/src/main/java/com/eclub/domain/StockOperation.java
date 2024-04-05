package com.eclub.domain;

import com.eclub.domain.Product.ProductId;
import com.eclub.domain.StockItem.BatchNumber;

import java.util.function.Function;

public sealed interface StockOperation permits Purchase, Sale {
    BatchNumber batchNumber();
    ProductId productId();

    int quantity();

    // JAVA21: migrate to switch pattern matching
    <T> T match(Function<Purchase, T> purchaseOp, Function<Sale, T> saleOp);
}
