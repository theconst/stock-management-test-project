package com.eclub.mapper;

import com.eclub.domain.Product;
import com.eclub.dto.response.ProductResponse;
import org.mapstruct.Mapper;

@Mapper(uses = ProductIdMapper.class, config = MappingConfiguration.class)
public interface ProductToProductResponseMapper {
    ProductResponse map(Product product);
}
