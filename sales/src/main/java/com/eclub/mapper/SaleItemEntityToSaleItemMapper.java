package com.eclub.mapper;

import com.eclub.entity.CustomerEntity;
import com.eclub.entity.SaleItemEntity;
import com.eclub.model.SaleItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = CustomerEntityToCustomerMapper.class, config = MappingConfiguration.class)
public interface SaleItemEntityToSaleItemMapper {

    @Mapping(target = "id", source = "saleItemEntity.saleId")
    SaleItem map(SaleItemEntity saleItemEntity, CustomerEntity customer);
}
