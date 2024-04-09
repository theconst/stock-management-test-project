package com.eclub.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
public record PurchaseRequest(
        @NotNull(message = "Product id is required")
        @Schema(description = "Id of the product") Long productId,
        @NotNull(message = "Product id is required")
        @Schema(description = "Batch number of the purchase") Long batchNumber,
        @NotNull(message = "Product id is required")
        @Schema(description = "Quantity of products added to stock") Integer quantity) {
}
