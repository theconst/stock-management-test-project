package com.eclub.mapper;

import com.eclub.dto.SaleDto;
import com.eclub.queue.message.RemoveFromStockMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MappingConfiguration.class)
public interface SaleDtoToRemoveFromStockMapper {
    @Mapping(target = "type", ignore = true)
    RemoveFromStockMessage map(SaleDto sale, String messageId);
}
