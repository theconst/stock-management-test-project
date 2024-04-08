package com.eclub.mapper;

import com.eclub.domain.SaleItem;
import com.eclub.dto.request.SaleRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {SaleItemIdMapper.class, CustomerIdMapper.class, StockItemIdMapper.class}, config = MappingConfiguration.class)
public interface SaleRequestToSellItemMapper {
    @Mapping(target = "id", ignore = true)
    SaleItem map(SaleRequest sale);
}
