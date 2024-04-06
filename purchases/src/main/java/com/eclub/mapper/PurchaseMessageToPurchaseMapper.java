package com.eclub.mapper;

import com.eclub.domain.AddToStock;
import com.eclub.queue.message.AddToStockMessage;
import org.mapstruct.Mapper;

@Mapper(uses = {ProductIdMapper.class, BatchNumberMapper.class}, config = MappingConfiguration.class)
public interface PurchaseMessageToPurchaseMapper {

    AddToStock map(AddToStockMessage purchase);
}
