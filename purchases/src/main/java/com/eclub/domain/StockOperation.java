package com.eclub.domain;

import java.util.function.Function;

public sealed interface StockOperation permits Purchase, Sale {

    // JAVA21: migrate to switch pattern matching
    <T> T match(Function<Purchase, T> purchaseOp, Function<Sale, T> saleOp);
}
