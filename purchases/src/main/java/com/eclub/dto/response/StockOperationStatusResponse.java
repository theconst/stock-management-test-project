package com.eclub.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record StockOperationStatusResponse(
        @Schema(description = "Id of stock operation") String stockOperationId,
        @Schema(description = "Status of operation. Non-submitted operations are PENDING") Status status) {

    public enum Status {
        PENDING, PROCESSED
    }

    public static StockOperationStatusResponse pending(String id) {
        return new StockOperationStatusResponse(id, Status.PENDING);
    }

    public static StockOperationStatusResponse processed(String id) {
        return new StockOperationStatusResponse(id, Status.PROCESSED);
    }
}
