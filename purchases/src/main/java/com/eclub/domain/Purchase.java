package com.eclub.domain;

import com.eclub.domain.Product.ProductId;
import com.eclub.domain.StockItem.BatchNumber;
import lombok.Builder;

@Builder
public record Purchase(ProductId productId, BatchNumber batchNumber, int quantity) {
}
