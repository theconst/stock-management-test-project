package com.eclub.dto.request;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

@Builder
@Jacksonized
public record SaleItemRequest(Long id, CustomerRequest customerRequest, BigDecimal price) {
}
