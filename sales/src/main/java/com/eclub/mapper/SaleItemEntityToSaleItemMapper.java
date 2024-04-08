package com.eclub.mapper;

import com.eclub.domain.SaleItem;
import com.eclub.entity.SaleItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {CustomerIdMapper.class, SaleItemIdMapper.class, CustomerEntityToCustomerMapper.class, StockItemIdMapper.class}, config = MappingConfiguration.class)
public interface SaleItemEntityToSaleItemMapper {

    @Mapping(target = "id", source = "saleId")
    SaleItem map(SaleItemEntity saleItemEntity);
}
