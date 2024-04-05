package com.eclub.mapper;

import com.eclub.domain.Purchase;
import com.eclub.queue.message.PurchaseMessage;
import org.mapstruct.Mapper;

@Mapper(uses = {ProductIdMapper.class, BatchNumberMapper.class}, config = MappingConfiguration.class)
public interface PurchaseMessageToPurchaseMapper {

    Purchase map(PurchaseMessage purchase);
}
