package com.eclub.mapper;

import com.eclub.entity.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(config = MappingConfiguration.class, nullValuePropertyMappingStrategy = IGNORE)
public interface CustomerUpdater {
    void update(CustomerEntity source, @MappingTarget CustomerEntity target);
}
