package com.eclub.mapper;

import com.eclub.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(config = MappingConfiguration.class, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductUpdater {

    /**
     * Set non-null fields from source to target
     */
    void map(ProductEntity source, @MappingTarget ProductEntity target);
}
