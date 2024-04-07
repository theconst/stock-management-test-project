package com.eclub.dto;

public record StockOperationStatusDto(String stockOperationId, Status status) {

    public enum Status {
        PENDING, PROCESSED
    }

    public static StockOperationStatusDto pending(String id) {
        return new StockOperationStatusDto(id, Status.PENDING);
    }

    public static StockOperationStatusDto processed(String id) {
        return new StockOperationStatusDto(id, Status.PROCESSED);
    }
}
