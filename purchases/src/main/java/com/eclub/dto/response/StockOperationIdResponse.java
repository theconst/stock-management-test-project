package com.eclub.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record StockOperationIdResponse(
        @Schema(description = "Stock operation id") String stockOperationId) {
}
