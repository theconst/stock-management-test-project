package com.eclub.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
public record StockItemResponse(
        Long id,
        ProductResponse product,
        @Schema(description = "Batch number") Long batchNumber,
        Integer quantity) {
}
