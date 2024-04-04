package com.eclub.mapper;

import com.eclub.domain.StockItem;
import com.eclub.entity.ProductEntity;
import com.eclub.entity.StockItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {StockItemIdMapper.class, BatchNumberMapper.class, ProductEntityToProductMapper.class},
        config = MappingConfiguration.class)
public interface StockItemEntityToStockItemMapper {

    @Mapping(target = "id", source = "stockItemEntity.stockItemId")
    StockItem map(StockItemEntity stockItemEntity, ProductEntity product);
}
