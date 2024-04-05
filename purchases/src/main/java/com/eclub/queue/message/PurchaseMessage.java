package com.eclub.queue.message;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
public final class PurchaseMessage implements StockTransactionMessage {
    @Builder.Default
    private final Type type = Type.PURCHASE;

    private final Long productId;
    private final Long batchNumber;
    private final Integer quantity;
}
