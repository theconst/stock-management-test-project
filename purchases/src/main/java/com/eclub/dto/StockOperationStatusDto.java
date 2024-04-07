package com.eclub.dto;

public record StockOperationStatusDto(String id, Status status) {

    public enum Status {
        PENDING, PROCESSED
    }
}
