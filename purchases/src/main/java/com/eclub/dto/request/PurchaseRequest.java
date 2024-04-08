package com.eclub.dto.request;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
public record PurchaseRequest(Long productId, Long batchNumber, Integer quantity) {
}
