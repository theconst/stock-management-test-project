package com.eclub.controller;

import com.eclub.dto.CustomerDto;
import com.eclub.mapper.CustomerDtoToCustomerMapper;
import com.eclub.mapper.CustomerToCustomerDtoMapper;
import com.eclub.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("customers")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerController {
    private final CustomerDtoToCustomerMapper customerDtoToCustomerMapper;
    private final CustomerToCustomerDtoMapper customerToCustomerDtoMapper;

    private final CustomerService customerService;

    @GetMapping("/{id}")
    public Mono<CustomerDto> findCustomerById(@PathVariable("id") Long id) {
        return customerService
                .findCustomerById(id)
                .map(customerToCustomerDtoMapper::map);
    }

    @PutMapping("/{id}")
    public Mono<CustomerDto> modifyCustomer(@RequestBody CustomerDto customer) {
        return customerService
                .upsert(customerDtoToCustomerMapper.map(customer))
                .map(customerToCustomerDtoMapper::map);
    }

    @PostMapping("/")
    public Mono<CustomerDto> createCustomer(@RequestBody CustomerDto customer) {
        return customerService
                .upsert(customerDtoToCustomerMapper.map(customer))
                .map(customerToCustomerDtoMapper::map);
    }

    @GetMapping("/")
    public Mono<Page<CustomerDto>> listCustomers(
            @RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "20") int pageSize) {
        return customerService
                .listCustomers(PageRequest.of(pageNumber, pageSize))
                .map(page -> page.map(customerToCustomerDtoMapper::map));
    }

}
