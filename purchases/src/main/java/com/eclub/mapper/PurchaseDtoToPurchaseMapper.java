package com.eclub.mapper;

import com.eclub.domain.AddToStock;
import com.eclub.dto.request.PurchaseRequest;
import org.mapstruct.Mapper;

@Mapper(uses = {ProductIdMapper.class, BatchNumberMapper.class, OperationIdMapper.class}, config = MappingConfiguration.class)
public interface PurchaseDtoToPurchaseMapper {
    AddToStock map(PurchaseRequest purchase, String operationId);
}
