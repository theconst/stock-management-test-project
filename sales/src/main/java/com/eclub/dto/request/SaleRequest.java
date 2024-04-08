package com.eclub.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

@Builder
@Jacksonized
public record SaleRequest(
        @NotNull
        @Schema(description = "Stock item id") Long stockItemId,
        @NotNull
        @Schema(description = "Customer id") Long customerId,
        @NotNull
        @Schema(description = "Total price of sale") BigDecimal price,
        @NotNull
        @Schema(description = "Number of items sold") Integer quantity) {
}
