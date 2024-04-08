package com.eclub.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
public record SaleCreatedResponse(
        @Schema(description = "Stock item id") String stockOperationId,
        @Schema(description = "Sale record") SaleResponse sale) {
}
