package com.eclub.mapper;

import com.eclub.domain.SaleItem;
import com.eclub.dto.request.SaleRequest;
import com.eclub.dto.response.SaleResponse;
import org.mapstruct.Mapper;

@Mapper(uses = {SaleItemIdMapper.class, CustomerIdMapper.class, StockItemIdMapper.class}, config = MappingConfiguration.class)
public interface SaleItemToSaleResponseMapper {

    SaleResponse map(SaleItem sale);
}
