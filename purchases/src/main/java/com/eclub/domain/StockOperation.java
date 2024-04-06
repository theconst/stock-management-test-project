package com.eclub.domain;

import java.util.function.Function;

public sealed interface StockOperation permits AddToStock, RemoveFromStock {

    record OperationId(String id) {
    }


    OperationId operationId();


    // JAVA21: migrate to switch pattern matching
    <T> T match(Function<AddToStock, T> purchaseOp, Function<RemoveFromStock, T> saleOp);
}
