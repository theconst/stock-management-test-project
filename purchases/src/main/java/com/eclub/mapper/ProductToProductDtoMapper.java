package com.eclub.mapper;

import com.eclub.domain.Product;
import com.eclub.dto.ProductDto;
import jakarta.annotation.Nonnull;
import org.mapstruct.Mapper;

@Mapper(uses = ProductIdMapper.class, config = MappingConfiguration.class)
public interface ProductToProductDtoMapper {
    ProductDto map(Product product);
}
