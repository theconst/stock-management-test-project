package com.eclub.mapper;

import com.eclub.domain.Customer;
import com.eclub.entity.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = CustomerIdMapper.class, config = MappingConfiguration.class)
public interface CustomerEntityToCustomerMapper {

    @Mapping(target = "id", source = "customerId")
    Customer map(CustomerEntity customerEntity);
}
