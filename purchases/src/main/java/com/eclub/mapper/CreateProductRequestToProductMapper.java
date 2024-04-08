package com.eclub.mapper;

import com.eclub.domain.Product;
import com.eclub.dto.request.CreateProductRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MappingConfiguration.class)
public interface CreateProductRequestToProductMapper {

    @Mapping(target = "id", ignore = true)
    Product map(CreateProductRequest productRequest);
}
