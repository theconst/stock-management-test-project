package com.eclub.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

@Builder
@Jacksonized
public record SaleRequest(
        @Schema(description = "Stock item id") Long stockItemId,
        @Schema(description = "Customer id") Long customerId,
        @Schema(description = "Total price of sale") BigDecimal price,
        @Schema(description = "Number of items sold") Integer quantity) {
}
