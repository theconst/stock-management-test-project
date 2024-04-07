package com.eclub.domain;

public record SaleRecord(SaleItem sale, RemoveFromStockOperationId stockOperationId) {
}
