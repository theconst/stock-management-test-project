package com.eclub.mapper;

import com.eclub.domain.SaleRecord;
import com.eclub.dto.SaleResponseDto;
import org.mapstruct.Mapper;

@Mapper(uses = SaleItemToSaleDtoMapper.class, config = MappingConfiguration.class)
public interface SaleRecordToSaleResponseDtoMapper {
    SaleResponseDto map(SaleRecord saleRecord);
}
