package com.eclub.dto;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

@Builder
@Jacksonized
public record SaleDto(Long id, Long customerId, BigDecimal price) {
}
