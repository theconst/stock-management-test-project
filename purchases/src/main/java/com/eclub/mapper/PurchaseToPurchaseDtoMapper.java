package com.eclub.mapper;

import com.eclub.domain.AddToStock;
import com.eclub.dto.request.PurchaseRequest;
import org.mapstruct.Mapper;

@Mapper(uses = {ProductIdMapper.class, BatchNumberMapper.class}, config = MappingConfiguration.class)
public interface PurchaseToPurchaseDtoMapper {

    PurchaseRequest map(AddToStock purchase);
}
