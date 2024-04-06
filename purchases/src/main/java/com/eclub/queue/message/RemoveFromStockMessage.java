package com.eclub.queue.message;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Builder
@Value
@Jacksonized
public class RemoveFromStockMessage implements StockTransactionMessage {
    @Builder.Default
    Type type = Type.REMOVE_FROM_STOCK;

    Long stockItemId;
    Integer quantity;
}
