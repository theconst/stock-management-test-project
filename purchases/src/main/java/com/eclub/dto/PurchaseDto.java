package com.eclub.dto;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
public record PurchaseDto(Long productId, Long batchNumber, Integer quantity) {
}
