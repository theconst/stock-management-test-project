package com.eclub.mapper;

import com.eclub.domain.Customer;
import com.eclub.dto.request.CreateCustomerRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MappingConfiguration.class)
public interface CreateCustomerRequestToCustomerMapper {
    @Mapping(target = "id", ignore = true)
    Customer map(CreateCustomerRequest customer);
}
