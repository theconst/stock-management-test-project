package com.eclub.service;

import com.eclub.domain.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerService {
    Mono<Customer> upsert(Customer customer);

    Mono<Page<Customer>> listCustomers(PageRequest page);

    Mono<Customer> findCustomerById(Long id);
}
