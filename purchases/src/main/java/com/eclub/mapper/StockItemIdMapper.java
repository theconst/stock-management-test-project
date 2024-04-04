package com.eclub.mapper;

import com.eclub.domain.StockItem.StockItemId;
import org.mapstruct.Mapper;

@Mapper(config = MappingConfiguration.class)
public interface StockItemIdMapper {
    default StockItemId map(Long stockItemId) {
        return stockItemId == null ? null : new StockItemId(stockItemId);
    }

    default Long map(StockItemId stockItemId) {
        return stockItemId == null ? null : stockItemId.id();
    }
}
