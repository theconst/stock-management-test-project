package com.eclub.dto;

import com.eclub.domain.StockItem.BatchNumber;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
public record StockItemDto(Long id, ProductDto product, BatchNumber batchNumber, Integer quantity) {
}
