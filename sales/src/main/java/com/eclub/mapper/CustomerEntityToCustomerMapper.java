package com.eclub.mapper;

import com.eclub.entity.CustomerEntity;
import com.eclub.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = CustomerIdMapper.class, config = MappingConfiguration.class)
public interface CustomerEntityToCustomerMapper {

    @Mapping(target = "id", source = "customerId")
    Customer map(CustomerEntity customerEntity);
}
