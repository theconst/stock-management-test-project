package com.eclub.mapper;

import com.eclub.domain.SaleItem;
import com.eclub.dto.SaleDto;
import org.mapstruct.Mapper;

@Mapper(config = MappingConfiguration.class)
public interface SaleItemToSaleDtoMapper {

    SaleDto map(SaleItem sale);
}
