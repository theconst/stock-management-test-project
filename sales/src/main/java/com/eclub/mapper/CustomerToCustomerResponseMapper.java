package com.eclub.mapper;

import com.eclub.domain.Customer;
import com.eclub.dto.response.CustomerResponse;
import org.mapstruct.Mapper;

@Mapper(uses = CustomerIdMapper.class, config = MappingConfiguration.class)
public interface CustomerToCustomerResponseMapper {

    CustomerResponse map(Customer customer);
}
