package com.eclub.controller;

import com.eclub.dto.PurchaseDto;
import com.eclub.mapper.MappingConfiguration;
import com.eclub.queue.message.AddToStockMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MappingConfiguration.class)
public interface PurchaseDtoToAddToStockMessageMapper {

    @Mapping(target = "type", expression = "java(com.eclub.queue.message.StockTransactionMessage.Type.ADD_TO_STOCK)")
    AddToStockMessage map(PurchaseDto dto, String messageId);
}
