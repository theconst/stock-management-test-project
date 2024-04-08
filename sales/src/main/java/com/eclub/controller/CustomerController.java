package com.eclub.controller;

import com.eclub.dto.request.CustomerRequest;
import com.eclub.dto.response.CustomerResponse;
import com.eclub.mapper.CustomerDtoToCustomerMapper;
import com.eclub.mapper.CustomerToCustomerResponseMapper;
import com.eclub.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("customers")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerController {
    private final CustomerDtoToCustomerMapper customerDtoToCustomerMapper;
    private final CustomerToCustomerResponseMapper customerToCustomerResponse;

    private final CustomerService customerService;

    @GetMapping("/{id}")
    public Mono<CustomerResponse> findCustomerById(@PathVariable("id") Long id) {
        return customerService
                .findCustomerById(id)
                .map(customerToCustomerResponse::map);
    }

    @PutMapping("/{id}")
    public Mono<CustomerResponse> modifyCustomer(@RequestBody CustomerRequest customer) {
        return customerService
                .upsert(customerDtoToCustomerMapper.map(customer))
                .map(customerToCustomerResponse::map);
    }

    @PostMapping("/")
    public Mono<CustomerResponse> createCustomer(@RequestBody CustomerRequest customer) {
        return customerService
                .upsert(customerDtoToCustomerMapper.map(customer))
                .map(customerToCustomerResponse::map);
    }

    @GetMapping("/")
    public Mono<Page<CustomerResponse>> listCustomers(
            @RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "20") int pageSize) {
        return customerService
                .listCustomers(PageRequest.of(pageNumber, pageSize))
                .map(page -> page.map(customerToCustomerResponse::map));
    }

}
