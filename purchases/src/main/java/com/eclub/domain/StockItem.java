package com.eclub.domain;

import lombok.Builder;

@Builder(toBuilder = true)
public record StockItem(StockItemId id, BatchNumber batchNumber, Product product, int quantity) {

    public record BatchNumber(long batchNumber) {
    }

    public record StockItemId(long id) {
    }
}
