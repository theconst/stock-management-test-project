package com.eclub.mapper;

import com.eclub.domain.AddToStock;
import com.eclub.domain.RemoveFromStock;
import com.eclub.domain.StockOperation;
import com.eclub.message.AddToStockMessage;
import com.eclub.message.RemoveFromStockMessage;
import com.eclub.message.StockOperationMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {ProductIdMapper.class, BatchNumberMapper.class, StockItemIdMapper.class, OperationIdMapper.class},
        config = MappingConfiguration.class)
public interface StockTransactionMessageToStockOperationMapper {

    default StockOperation map(StockOperationMessage message) {
        if (message == null) {
            return null;
        }
        // JAVA21: migrate to switch pattern matching
        if (message instanceof AddToStockMessage pm) {
            return map(pm);
        } else if (message instanceof RemoveFromStockMessage sm) {
            return map(sm);
        }
        throw new IllegalArgumentException("Transaction type %s not supported".formatted(message));
    }

    @Mapping(target = "operationId", source = "messageId")
    AddToStock map(AddToStockMessage addToStockMessage);

    @Mapping(target = "operationId", source = "messageId")
    RemoveFromStock map(RemoveFromStockMessage removeFromStockMessage);
}
