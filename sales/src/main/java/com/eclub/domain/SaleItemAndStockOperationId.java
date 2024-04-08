package com.eclub.domain;

public record SaleItemAndStockOperationId(SaleItem sale, RemoveFromStockOperationId stockOperationId) {
}
