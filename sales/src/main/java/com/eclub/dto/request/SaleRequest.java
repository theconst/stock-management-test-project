package com.eclub.dto.request;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

@Builder
@Jacksonized
public record SaleRequest(Long id, Long stockItemId, Long customerId, BigDecimal price, Integer quantity) {
}
