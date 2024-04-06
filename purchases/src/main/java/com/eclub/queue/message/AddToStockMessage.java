package com.eclub.queue.message;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Builder
@Value
@Jacksonized
public class AddToStockMessage implements StockTransactionMessage {
    @Builder.Default
    Type type = Type.ADD_TO_STOCK;
    String messageId;
    Long productId;
    Long batchNumber;
    Integer quantity;
}
