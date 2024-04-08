package com.eclub.mapper;

import com.eclub.domain.SaleItem;
import com.eclub.entity.SaleItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {SaleItemIdMapper.class, CustomerIdMapper.class, StockItemIdMapper.class}, config = MappingConfiguration.class)
public interface SaleItemToSaleItemEntityMapper {
    @Mapping(target = "saleId", source = "saleItem.id")
    SaleItemEntity map(SaleItem saleItem);
}
