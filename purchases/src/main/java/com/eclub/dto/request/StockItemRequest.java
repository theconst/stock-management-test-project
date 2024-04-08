package com.eclub.dto.request;

import com.eclub.domain.StockItem.BatchNumber;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
public record StockItemRequest(Long id, ProductRequest product, BatchNumber batchNumber, Integer quantity) {
}
