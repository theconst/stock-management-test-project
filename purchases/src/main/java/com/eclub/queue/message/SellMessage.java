package com.eclub.queue.message;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
public final class SellMessage implements StockTransactionMessage {
    @Builder.Default
    private final Type type = Type.SELL;

    private final Long productId;
    private final Long batchNumber;
    private final Integer quantity;
}
