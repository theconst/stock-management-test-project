package com.eclub.mapper;

import com.eclub.domain.StockItemId;
import org.mapstruct.Mapper;

@Mapper(config = MappingConfiguration.class)
public interface StockItemIdMapper {

    default Long map(StockItemId id) {
        return id == null ? null : id.id();
    }

    default StockItemId map(Long id) {
        return id == null ? null : new StockItemId(id);
    }
}
