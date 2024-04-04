package com.eclub.mapper;

import com.eclub.domain.Product;
import jakarta.annotation.Nonnull;
import org.mapstruct.Mapper;

@Mapper(config = MappingConfiguration.class)
public interface ProductIdMapper {
    default Product.ProductId map(long productId) {
        return new Product.ProductId(productId);
    }

    default long map(@Nonnull Product.ProductId productId) {
        return productId.id();
    }
}
