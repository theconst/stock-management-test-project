package com.eclub.mapper;

import com.eclub.domain.Product;
import com.eclub.dto.request.ModifyProductRequest;
import org.mapstruct.Mapper;

@Mapper(uses = ProductIdMapper.class, config = MappingConfiguration.class)
public interface ModifyProductRequestToProductMapper {
    Product map(ModifyProductRequest productRequest);
}