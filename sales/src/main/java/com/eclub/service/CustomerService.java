package com.eclub.service;

import com.eclub.domain.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import reactor.core.publisher.Mono;

public interface CustomerService {
    Mono<Customer> createCustomer(Customer customer);
    Mono<Customer> updateCustomer(Customer customer);
    Mono<Void> deleteCustomer(Long id);

    Mono<Page<Customer>> listCustomers(PageRequest page);

    Mono<Customer> findCustomerById(Long id);


}
