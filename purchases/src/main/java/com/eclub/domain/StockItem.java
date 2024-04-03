package com.eclub.domain;

import lombok.Builder;

@Builder
public record StockItem(StockItemId id, Product product, Price sellingPrice, int quantity) {

    public record StockItemId(long id) {}
}
