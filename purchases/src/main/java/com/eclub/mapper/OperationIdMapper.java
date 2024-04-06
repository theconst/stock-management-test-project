package com.eclub.mapper;

import com.eclub.domain.StockOperation.OperationId;
import org.mapstruct.Mapper;

@Mapper(config = MappingConfiguration.class)
public interface OperationIdMapper {
    default OperationId map(String id) {
        return id == null ? null : new OperationId(id);
    }

    default String map(OperationId id) {
        return id == null ? null : id.id();
    }
}
