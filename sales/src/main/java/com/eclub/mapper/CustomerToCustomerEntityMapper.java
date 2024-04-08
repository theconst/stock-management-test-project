package com.eclub.mapper;

import com.eclub.domain.Customer;
import com.eclub.entity.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = CustomerIdMapper.class, config = MappingConfiguration.class)
public interface CustomerToCustomerEntityMapper {

    @Mapping(target = "customerId", source = "id")
    CustomerEntity map(Customer customer);
}
