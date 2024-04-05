package com.eclub.queue.message;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @Type(value = SellMessage.class, name = "SELL"),
        @Type(value = PurchaseMessage.class, name = "PURCHASE")
})
public sealed interface StockTransactionMessage permits PurchaseMessage, SellMessage {

    enum Type {
        PURCHASE, SELL
    }
}
