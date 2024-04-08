package com.eclub.dto.response;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

@Builder
@Jacksonized
public record SaleResponse(Long id, Long stockItemId, Long customerId, BigDecimal price, Integer quantity) {
}
