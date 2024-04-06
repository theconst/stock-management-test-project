package com.eclub.mapper;

import com.eclub.dto.SaleDto;
import com.eclub.domain.SaleItem;
import org.mapstruct.Mapper;

@Mapper(uses = {SaleItemIdMapper.class, CustomerIdMapper.class}, config = MappingConfiguration.class)
public interface SaleDtoToSellItemMapper {
    SaleItem map(SaleDto sale);
}
