package com.eclub.queue.message;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @Type(value = RemoveFromStockMessage.class, name = "REMOVE_FROM_STOCK"),
        @Type(value = AddToStockMessage.class, name = "ADD_TO_STOCK")
})
public sealed interface StockTransactionMessage permits AddToStockMessage, RemoveFromStockMessage {

    enum Type {
        ADD_TO_STOCK, REMOVE_FROM_STOCK
    }

    String getMessageId();
}
