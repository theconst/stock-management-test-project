package com.eclub.domain;

import com.eclub.domain.StockItem.BatchNumber;

import java.util.function.Function;

public record Sale(Product.ProductId productId, BatchNumber batchNumber, int quantity) implements StockOperation {

    @Override
    public <T> T match(Function<Purchase, T> purchaseOp, Function<Sale, T> saleOp) {
        return saleOp.apply(this);
    }
}
