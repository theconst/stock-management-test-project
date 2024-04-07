package com.eclub.mapper;

import com.eclub.domain.AddToStock;
import com.eclub.message.AddToStockMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {ProductIdMapper.class, BatchNumberMapper.class, OperationIdMapper.class}, config = MappingConfiguration.class)
public interface AddToStockMessageToAddToStockMapper {

    @Mapping(target = "operationId", source = "messageId")
    AddToStock map(AddToStockMessage purchase);
}
