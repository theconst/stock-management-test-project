package com.eclub.service;

import com.eclub.mapper.CustomerEntityToCustomerMapper;
import com.eclub.mapper.CustomerToCustomerEntityMapper;
import com.eclub.domain.Customer;
import com.eclub.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerToCustomerEntityMapper customerToCustomerEntityMapper;
    private final CustomerEntityToCustomerMapper customerEntityToCustomerMapper;

    @Override
    public Mono<Customer> upsert(Customer customer) {
        return customerRepository
                .save(customerToCustomerEntityMapper.map(customer))
                .map(customerEntityToCustomerMapper::map);
    }

    @Override
    public Mono<Page<Customer>> listCustomers(PageRequest page) {
        return customerRepository
                .findAllByOrderByCustomerId(page)
                .map(customerEntityToCustomerMapper::map)
                .collectList()
                .map(result -> new PageImpl<>(result, page, result.size()));
    }

    @Override
    public Mono<Customer> findCustomerById(Long id) {
        return customerRepository
                .findById(id)
                .map(customerEntityToCustomerMapper::map);
    }
}
