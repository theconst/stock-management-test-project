package com.eclub.dto.response;

import com.eclub.dto.doc.StockItemId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
public record SaleCreatedResponse(
        @StockItemId String stockOperationId,
        @Schema(description = "Sale record") SaleResponse sale) {
}
