package com.eclub.mapper;

import com.eclub.domain.SaleItemAndStockOperationId;
import com.eclub.dto.response.SaleCreatedResponse;
import org.mapstruct.Mapper;

@Mapper(uses = {
        SaleItemToSaleResponseMapper.class,
        RemoveFromStockOperationIdMapper.class,
        StockItemIdMapper.class,
        SaleItemIdMapper.class,
        CustomerIdMapper.class
}, config = MappingConfiguration.class)
public interface SaleItemAndStockOperationIdToSaleCreatedResponseMapper {
    SaleCreatedResponse map(SaleItemAndStockOperationId saleItemAndStockOperationId);
}
