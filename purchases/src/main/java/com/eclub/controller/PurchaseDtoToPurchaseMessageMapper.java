package com.eclub.controller;

import com.eclub.dto.PurchaseDto;
import com.eclub.mapper.MappingConfiguration;
import com.eclub.queue.message.PurchaseMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MappingConfiguration.class)
public interface PurchaseDtoToPurchaseMessageMapper {

    @Mapping(target = "type", expression = "java(com.eclub.queue.message.StockTransactionMessage.Type.PURCHASE)")
    PurchaseMessage map(PurchaseDto dto);
}
