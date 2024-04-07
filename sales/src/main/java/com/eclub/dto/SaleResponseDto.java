package com.eclub.dto;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
public record SaleResponseDto(String stockOperationId, SaleDto sale) {
}
