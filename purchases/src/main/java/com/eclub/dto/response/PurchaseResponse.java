package com.eclub.dto.response;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
public record PurchaseResponse(Long productId, Long batchNumber, Integer quantity) {
}
