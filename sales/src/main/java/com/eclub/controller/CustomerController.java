package com.eclub.controller;

import com.eclub.dto.request.CreateCustomerRequest;
import com.eclub.dto.request.ModifyCustomerRequest;
import com.eclub.dto.response.CustomerResponse;
import com.eclub.mapper.CreateCustomerRequestToCustomerMapper;
import com.eclub.mapper.ModifyCustomerRequestToCustomerMapper;
import com.eclub.mapper.CustomerToCustomerResponseMapper;
import com.eclub.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
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
    private final ModifyCustomerRequestToCustomerMapper modifyCustomerRequestToCustomerMapper;
    private final CustomerToCustomerResponseMapper customerToCustomerResponse;
    private final CreateCustomerRequestToCustomerMapper createCustomerRequestToCustomerMapper;

    private final CustomerService customerService;

    @GetMapping("/{id}")
    @Operation(summary = "Find customer by id")
    public Mono<CustomerResponse> findCustomerById(@PathVariable("id") Long id) {
        return customerService
                .findCustomerById(id)
                .map(customerToCustomerResponse::map);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modify customer")
    public Mono<CustomerResponse> modifyCustomer(@RequestBody ModifyCustomerRequest customer) {
        return customerService
                .upsert(modifyCustomerRequestToCustomerMapper.map(customer))
                .map(customerToCustomerResponse::map);
    }

    @PostMapping("/")
    @Operation(summary = "Create new customer")
    public Mono<CustomerResponse> createCustomer(@RequestBody CreateCustomerRequest customer) {
        return customerService
                .upsert(createCustomerRequestToCustomerMapper.map(customer))
                .map(customerToCustomerResponse::map);
    }

    @GetMapping("/")
    @Operation(summary = "List customers. Pagination is done by id")
    public Mono<Page<CustomerResponse>> listCustomers(
            @RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "20") int pageSize) {
        return customerService
                .listCustomers(PageRequest.of(pageNumber, pageSize))
                .map(page -> page.map(customerToCustomerResponse::map));
    }

}
