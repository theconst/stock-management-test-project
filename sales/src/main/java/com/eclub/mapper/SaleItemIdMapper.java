package com.eclub.mapper;

import com.eclub.model.SaleItem.SaleItemId;
import org.mapstruct.Mapper;

@Mapper(config = MappingConfiguration.class)
public interface SaleItemIdMapper {

    default SaleItemId map(Long id) {
        return id == null ? null : new SaleItemId(id);
    }

    default Long map(SaleItemId id) {
        return id == null ? null : id.id();
    }
}
