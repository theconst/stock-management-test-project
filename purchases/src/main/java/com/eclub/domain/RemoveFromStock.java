package com.eclub.domain;

import com.eclub.domain.StockItem.StockItemId;
import lombok.Builder;

import java.util.function.Function;

@Builder
public record RemoveFromStock(OperationId operationId, StockItemId stockItemId, int quantity) implements StockOperation {

    @Override
    public <T> T match(Function<AddToStock, T> ignored, Function<RemoveFromStock, T> removeFromStockOp) {
        return removeFromStockOp.apply(this);
    }
}
