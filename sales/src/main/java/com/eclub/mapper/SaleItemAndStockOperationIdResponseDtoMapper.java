package com.eclub.mapper;

import com.eclub.domain.SaleItemAndStockOperationId;
import com.eclub.dto.SaleResponseDto;
import org.mapstruct.Mapper;

@Mapper(uses = {SaleItemToSaleDtoMapper.class, RemoveFromStockOperationIdMapper.class}, config = MappingConfiguration.class)
public interface SaleItemAndStockOperationIdResponseDtoMapper {
    SaleResponseDto map(SaleItemAndStockOperationId saleItemAndStockOperationId);
}
