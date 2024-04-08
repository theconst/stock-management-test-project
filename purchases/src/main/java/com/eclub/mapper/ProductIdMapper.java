package com.eclub.mapper;

import com.eclub.domain.Product;
import org.mapstruct.Mapper;

@Mapper(config = MappingConfiguration.class)
public interface ProductIdMapper {
    default Product.ProductId map(Long productId) {
        return productId == null ? null : new Product.ProductId(productId);
    }

    default Long map(Product.ProductId productId) {
        return productId == null ? null : productId.id();
    }
}
