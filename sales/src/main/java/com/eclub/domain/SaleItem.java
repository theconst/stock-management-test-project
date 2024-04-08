package com.eclub.domain;

import com.eclub.domain.Customer.CustomerId;
import lombok.Builder;

import java.math.BigDecimal;

@Builder(toBuilder = true)
public record SaleItem(SaleItemId id, CustomerId customerId, StockItemId stockItemId, BigDecimal price, int quantity) {

    public record SaleItemId(Long id) {
    }
}
