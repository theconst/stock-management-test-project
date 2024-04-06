package com.eclub.controller;

import com.eclub.dto.CustomerDto;
import com.eclub.mapper.CustomerDtoToCustomerMapper;
import com.eclub.mapper.CustomerToCustomerDtoMapper;
import com.eclub.domain.Page;
import com.eclub.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
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

    @PutMapping("/{id}") //TODO: validate
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
    public Flux<CustomerDto> listCustomers(
            @RequestParam(value = "path", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "20") int size) {
        return customerService
                .listCustomers(new Page(page, size))
                .map(customerToCustomerDtoMapper::map);
    }

}
