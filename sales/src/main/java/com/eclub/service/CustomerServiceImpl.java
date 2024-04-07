package com.eclub.service;

import com.eclub.mapper.CustomerEntityToCustomerMapper;
import com.eclub.mapper.CustomerToCustomerEntityMapper;
import com.eclub.domain.Customer;
import com.eclub.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static com.eclub.util.PagingCollector.collectPages;

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
    public Mono<Page<Customer>> listCustomers(PageRequest pageRequest) {
        return customerRepository
                .findAllByOrderByCustomerId(pageRequest)
                .map(customerEntityToCustomerMapper::map)
                .transform(collectPages(pageRequest, customerRepository::count))
                .single();
    }

    @Override
    public Mono<Customer> findCustomerById(Long id) {
        return customerRepository
                .findById(id)
                .map(customerEntityToCustomerMapper::map);
    }
}
