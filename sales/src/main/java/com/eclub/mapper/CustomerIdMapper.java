package com.eclub.mapper;

import com.eclub.model.Customer.CustomerId;
import org.mapstruct.Mapper;

@Mapper(config = MappingConfiguration.class)
public interface CustomerIdMapper {
    default CustomerId map(Long id) {
        return id == null ? null : new CustomerId(id);
    }

    default Long map(CustomerId id) {
        return id == null ? null : id.id();
    }
}
