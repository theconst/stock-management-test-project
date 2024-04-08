package com.eclub.dto.response;

import com.eclub.domain.StockItem.BatchNumber;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
public record StockItemResponse(
        Long id,
        ProductResponse product,
        @Schema(name = "Batch number") BatchNumber batchNumber,
        Integer quantity) {
}
