package com.eclub.mapper;

import com.eclub.dto.request.PurchaseRequest;
import com.eclub.message.AddToStockMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MappingConfiguration.class)
public interface PurchaseRequestToAddToStockMessageMapper {

    @Mapping(target = "type", expression = "java(com.eclub.message.StockOperationMessage.Type.ADD_TO_STOCK)")
    AddToStockMessage map(PurchaseRequest dto, String messageId);
}
