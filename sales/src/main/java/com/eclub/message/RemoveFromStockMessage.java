package com.eclub.message;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Builder
@Value
@Jacksonized
public class RemoveFromStockMessage {
    @Builder.Default
    String type = "REMOVE_FROM_STOCK";
    String messageId;
    Long stockItemId;
    Integer quantity;
}
