package com.eclub.message;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Builder
@Value
@Jacksonized
public class AddToStockMessage implements StockOperationMessage {
    @Builder.Default
    Type type = Type.ADD_TO_STOCK;
    String messageId;
    Long productId;
    Long batchNumber;
    Integer quantity;
}
