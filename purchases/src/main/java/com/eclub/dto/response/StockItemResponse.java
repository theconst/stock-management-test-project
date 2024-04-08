package com.eclub.dto.response;

import com.eclub.domain.StockItem.BatchNumber;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
public record StockItemResponse(Long id, ProductResponse product, BatchNumber batchNumber, Integer quantity) {
}
