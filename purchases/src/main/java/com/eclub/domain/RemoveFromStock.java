package com.eclub.domain;

import java.util.function.Function;

public record RemoveFromStock(StockItem.StockItemId stockItemId, int quantity) implements StockOperation {

    @Override
    public <T> T match(Function<AddToStock, T> ignored, Function<RemoveFromStock, T> removeFromStockOp) {
        return removeFromStockOp.apply(this);
    }
}
