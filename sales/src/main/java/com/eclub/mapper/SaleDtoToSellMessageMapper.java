package com.eclub.mapper;

import com.eclub.dto.SaleDto;
import com.eclub.queue.message.SellMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MappingConfiguration.class)
public interface SaleDtoToSellMessageMapper {

    @Mapping(target = "type", ignore = true)
    SellMessage map(SaleDto sale);
}
