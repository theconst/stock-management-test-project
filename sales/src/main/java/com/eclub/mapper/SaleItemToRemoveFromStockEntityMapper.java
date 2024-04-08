package com.eclub.mapper;

import com.eclub.domain.SaleItem;
import com.eclub.entity.RemoveFromStockEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = StockItemIdMapper.class, config = MappingConfiguration.class)
public interface SaleItemToRemoveFromStockEntityMapper {
    @Mapping(target = "new", ignore = true)
    RemoveFromStockEntity map(SaleItem saleItem, String operationId);
}
