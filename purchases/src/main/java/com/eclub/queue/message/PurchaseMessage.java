package com.eclub.queue.message;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Builder
@Value
@Jacksonized
public class PurchaseMessage implements StockTransactionMessage {
    @Builder.Default
    Type type = Type.PURCHASE;

    Long productId;
    Long batchNumber;
    Integer quantity;
}
