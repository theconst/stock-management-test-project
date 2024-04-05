package com.eclub.domain;

import com.eclub.domain.Product.ProductId;
import com.eclub.domain.StockItem.BatchNumber;
import lombok.Builder;

import java.util.function.Function;

@Builder
public record Purchase(ProductId productId, BatchNumber batchNumber, int quantity) implements StockOperation {
    @Override
    public <T> T match(Function<Purchase, T> purchaseOp, Function<Sale, T> saleOp) {
        return purchaseOp.apply(this);
    }
}
