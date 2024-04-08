package com.eclub.mapper;

import com.eclub.domain.RemoveFromStockOperationId;
import org.mapstruct.Mapper;

@Mapper(config = MappingConfiguration.class)
public interface RemoveFromStockOperationIdMapper {

    default RemoveFromStockOperationId map(String id) {
        return id == null ? null : new RemoveFromStockOperationId(id);
    }

    default String map(RemoveFromStockOperationId id) {
        return id == null ? null : id.id();
    }

}
