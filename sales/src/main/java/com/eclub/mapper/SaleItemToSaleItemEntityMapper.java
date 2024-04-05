package com.eclub.mapper;

import com.eclub.entity.SaleItemEntity;
import com.eclub.model.SaleItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MappingConfiguration.class)
public interface SaleItemToSaleItemEntityMapper {
    @Mapping(target = "saleId", source = "saleItem.id")
    @Mapping(target = "customerId", source = "customer.id")
    SaleItemEntity map(SaleItem saleItem, Long stockItemId);
}
