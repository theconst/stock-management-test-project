package com.eclub.mapper;

import com.eclub.domain.StockItem;
import com.eclub.dto.response.StockItemResponse;
import org.mapstruct.Mapper;

@Mapper(uses = {StockItemIdMapper.class, BatchNumberMapper.class, ProductIdMapper.class}, config = MappingConfiguration.class)
public interface StockItemToStockItemResponseMapper {
    StockItemResponse map(StockItem stockItem);
}
