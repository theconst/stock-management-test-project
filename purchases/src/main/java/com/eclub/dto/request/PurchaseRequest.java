package com.eclub.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
public record PurchaseRequest(
        @Schema(name = "Product id", description = "Id of the product") Long productId,
        @Schema(name = "Batch number", description = "Batch number of the purchase") Long batchNumber,
        Integer quantity) {
}
