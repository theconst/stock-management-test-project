package com.eclub.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
public record PurchaseRequest(
        @NotNull
        @Schema(description = "Id of the product") Long productId,
        @NotNull
        @Schema(description = "Batch number of the purchase") Long batchNumber,
        @NotNull
        @Schema(description = "Quantity of products added to stock") Integer quantity) {
}
