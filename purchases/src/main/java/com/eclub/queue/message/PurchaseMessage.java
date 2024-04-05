package com.eclub.queue.message;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
public record PurchaseMessage(Long productId, Long batchNumber, Integer quantity) {
}
