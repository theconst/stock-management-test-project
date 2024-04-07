package com.eclub.controller;

import com.eclub.message.AddToStockMessage;
import com.eclub.dto.PurchaseDto;
import com.eclub.mapper.MappingConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MappingConfiguration.class)
public interface PurchaseDtoToAddToStockMessageMapper {

    @Mapping(target = "type", expression = "java(com.eclub.domain.message.StockTransactionMessage.Type.ADD_TO_STOCK)")
    AddToStockMessage map(PurchaseDto dto, String messageId);
}
