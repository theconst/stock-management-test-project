package com.eclub.mapper;

import com.eclub.dto.CustomerDto;
import com.eclub.domain.Customer;
import org.mapstruct.Mapper;

@Mapper(uses = CustomerIdMapper.class, config = MappingConfiguration.class)
public interface CustomerDtoToCustomerMapper {


    Customer map(CustomerDto customerDto);
}
