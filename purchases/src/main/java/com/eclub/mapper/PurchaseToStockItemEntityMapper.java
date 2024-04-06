package com.eclub.mapper;

import com.eclub.domain.AddToStock;
import com.eclub.entity.StockItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {BatchNumberMapper.class, ProductIdMapper.class}, config = MappingConfiguration.class)
public interface PurchaseToStockItemEntityMapper {
    @Mapping(target = "stockItemId", ignore = true)
    StockItemEntity map(AddToStock purchase);
}
