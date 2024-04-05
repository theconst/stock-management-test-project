package com.eclub.queue.message;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Builder
@Value
@Jacksonized
public class SellMessage {
    @Builder.Default
    String type = "SELL"; //TODO(kkovalchuk): probably sales should also work with stock

    Long productId;
    Long batchNumber;
    Integer quantity;
}
