package com.eclub.mapper;

import com.eclub.entity.RemoveFromStockEntity;
import com.eclub.message.RemoveFromStockMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MappingConfiguration.class)
public interface RemoveFromStockEntityToRemoveFromStockMessageMapper {

    @Mapping(target = "type", ignore = true)
    @Mapping(target = "messageId", source = "operationId")
    RemoveFromStockMessage map(RemoveFromStockEntity removeFromStockEntity);
}
