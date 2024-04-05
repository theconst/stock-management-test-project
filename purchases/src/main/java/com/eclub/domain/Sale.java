package com.eclub.domain;

import java.util.function.Function;

public record Sale(StockItem.StockItemId stockItemId, int quantity) implements StockOperation {

    @Override
    public <T> T match(Function<Purchase, T> purchaseOp, Function<Sale, T> saleOp) {
        return saleOp.apply(this);
    }
}
