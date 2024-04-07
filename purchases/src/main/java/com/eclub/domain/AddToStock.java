package com.eclub.domain;

import com.eclub.domain.Product.ProductId;
import com.eclub.domain.StockItem.BatchNumber;
import lombok.Builder;

import java.util.function.Function;

@Builder
public record AddToStock(OperationId operationId, ProductId productId, BatchNumber batchNumber, int quantity) implements StockOperation {
    @Override
    public <T> T match(Function<AddToStock, T> addToStockOp, Function<RemoveFromStock, T> ignored) {
        return addToStockOp.apply(this);
    }
}
