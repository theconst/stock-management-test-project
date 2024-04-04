package com.eclub.mapper;

import com.eclub.domain.StockItem;
import com.eclub.entity.StockItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {StockItemIdMapper.class, BatchNumberMapper.class, ProductIdMapper.class}, config = MappingConfiguration.class)
public interface StockItemToStockItemEntityMapper {

    @Mapping(target = "stockItemId", source = "id")
    @Mapping(target = "productId", source = "product.id")
    StockItemEntity map(StockItem stockItem);
}
