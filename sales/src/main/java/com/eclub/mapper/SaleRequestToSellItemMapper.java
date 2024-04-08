package com.eclub.mapper;

import com.eclub.dto.request.SaleRequest;
import com.eclub.domain.SaleItem;
import org.mapstruct.Mapper;

@Mapper(uses = {SaleItemIdMapper.class, CustomerIdMapper.class, StockItemIdMapper.class}, config = MappingConfiguration.class)
public interface SaleRequestToSellItemMapper {
    SaleItem map(SaleRequest sale);
}
