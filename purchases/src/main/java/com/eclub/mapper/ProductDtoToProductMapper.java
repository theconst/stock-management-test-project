package com.eclub.mapper;

import com.eclub.domain.Product;
import com.eclub.dto.request.ProductRequest;
import org.mapstruct.Mapper;

@Mapper(uses = ProductIdMapper.class, config = MappingConfiguration.class)
public interface ProductDtoToProductMapper {
    Product map(ProductRequest productRequest);
}