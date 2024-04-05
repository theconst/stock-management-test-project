package com.eclub.dto;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

@Builder
@Jacksonized
public record SaleItemDto(Long id, CustomerDto customerDto, BigDecimal price) {
}
