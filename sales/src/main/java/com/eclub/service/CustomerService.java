package com.eclub.service;

import com.eclub.model.Customer;
import com.eclub.model.Page;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerService {

    Mono<Customer> upsert(Customer customer);


    Flux<Customer> listCustomers(Page page);

    Mono<Customer> findCustomerById(Long id);
}
