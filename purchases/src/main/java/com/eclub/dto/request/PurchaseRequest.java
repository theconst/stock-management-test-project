package com.eclub.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
public record PurchaseRequest(
        @Schema(description = "Id of the product") Long productId,
        @Schema(description = "Batch number of the purchase") Long batchNumber,
        @Schema(description = "Quantity of products added to stock") Integer quantity) {
}
