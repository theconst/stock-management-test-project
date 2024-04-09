package com.eclub.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

@Builder
@Jacksonized
public record SaleRequest(
        @NotNull(message = "Stock item id is required")
        @Schema(description = "Stock item id") Long stockItemId,
        @NotNull(message = "Customer id is required")
        @Schema(description = "Customer id") Long customerId,
        @NotNull(message = "Price is required")
        @Schema(description = "Total price of sale") BigDecimal price,
        @NotNull(message = "Quantity is required")
        @Schema(description = "Number of items sold") Integer quantity) {
}
