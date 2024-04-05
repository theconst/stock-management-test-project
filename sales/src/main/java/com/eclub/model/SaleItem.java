package com.eclub.model;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record SaleItem(SaleItemId id, Customer customer, BigDecimal price) {

    public record SaleItemId(Long id) {
    }
}
