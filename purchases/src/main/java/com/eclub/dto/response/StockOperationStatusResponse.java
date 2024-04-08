package com.eclub.dto.response;

public record StockOperationStatusResponse(String stockOperationId, Status status) {

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
