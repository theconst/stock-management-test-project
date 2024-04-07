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

    default StockOperation map(StockOperationMessage transaction) {
        // JAVA21: migrate to switch pattern matching
        if (transaction instanceof AddToStockMessage pm) {
            return map(pm);
        } else if (transaction instanceof RemoveFromStockMessage sm) {
            return map(sm);
        }
        throw new IllegalArgumentException("Transaction type %s not supported".formatted(transaction));
    }

    @Mapping(target = "operationId", source = "messageId")
    AddToStock map(AddToStockMessage addToStockMessage);

    @Mapping(target = "operationId", source = "messageId")
    RemoveFromStock map(RemoveFromStockMessage removeFromStockMessage);
}
