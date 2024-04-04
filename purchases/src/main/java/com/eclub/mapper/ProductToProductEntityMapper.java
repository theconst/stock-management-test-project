package com.eclub.mapper;

import com.eclub.domain.Product;
import com.eclub.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = ProductIdMapper.class, config = MappingConfiguration.class)
public interface ProductToProductEntityMapper {
    @Mapping(target = "productId", source = "id")
    ProductEntity map(Product product);
}
