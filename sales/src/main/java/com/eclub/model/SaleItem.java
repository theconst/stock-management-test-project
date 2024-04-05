package com.eclub.model;

import com.eclub.model.Customer.CustomerId;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record SaleItem(SaleItemId id, CustomerId customerId, Long stockItemId, BigDecimal price, int quantity) {

    public record SaleItemId(Long id) {
    }
}
