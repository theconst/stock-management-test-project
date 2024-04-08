package com.eclub.mapper;

import com.eclub.domain.Customer;
import com.eclub.dto.request.CustomerRequest;
import org.mapstruct.Mapper;

@Mapper(uses = CustomerIdMapper.class, config = MappingConfiguration.class)
public interface CustomerDtoToCustomerMapper {


    Customer map(CustomerRequest customerRequest);
}
