package com.eclub.message;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Builder
@Value
@Jacksonized
public class RemoveFromStockMessage implements StockOperationMessage {
    @Builder.Default
    Type type = Type.REMOVE_FROM_STOCK;
    String messageId;
    Long stockItemId;
    Integer quantity;
}
