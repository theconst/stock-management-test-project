package com.eclub.controller;

import com.eclub.dto.PurchaseDto;
import com.eclub.mapper.MappingConfiguration;
import com.eclub.queue.message.PurchaseMessage;
import org.mapstruct.Mapper;

@Mapper(config = MappingConfiguration.class)
public interface PurchaseDtoToPurchaseMessageMapper {

    PurchaseMessage map(PurchaseDto dto);
}
