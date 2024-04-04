package com.eclub.mapper;

import com.eclub.domain.Purchase;
import com.eclub.dto.PurchaseDto;
import org.mapstruct.Mapper;

@Mapper(uses = {ProductIdMapper.class, BatchNumberMapper.class}, config = MappingConfiguration.class)
public interface PurchaseDtoToPurchaseMapper {
    Purchase map(PurchaseDto purchase);
}
