package com.eclub.queue.message;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Builder
@Value
@Jacksonized
public class SellMessage implements StockTransactionMessage {
    @Builder.Default
    Type type = Type.SELL;

    Long stockItemId;
    Integer quantity;
}
