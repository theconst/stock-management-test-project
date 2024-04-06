package com.eclub.mapper;

import com.eclub.domain.AddToStock;
import com.eclub.dto.PurchaseDto;
import org.mapstruct.Mapper;

@Mapper(uses = {ProductIdMapper.class, BatchNumberMapper.class}, config = MappingConfiguration.class)
public interface PurchaseToPurchaseDtoMapper {

    PurchaseDto map(AddToStock purchase);
}
