package com.eclub.service;

import com.eclub.domain.Customer;
import com.eclub.domain.exception.NotFoundException;
import com.eclub.entity.CustomerEntity;
import com.eclub.mapper.CustomerEntityToCustomerMapper;
import com.eclub.mapper.CustomerToCustomerEntityMapper;
import com.eclub.mapper.CustomerUpdater;
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
    private final CustomerUpdater customerUpdater;

    @Override
    public Mono<Customer> createCustomer(Customer customer) {
        return customerRepository
                .save(customerToCustomerEntityMapper.map(customer))
                .map(customerEntityToCustomerMapper::map);
    }

    @Override
    public Mono<Customer> updateCustomer(Customer fieldToUpdate) {
        CustomerEntity customer = customerToCustomerEntityMapper.map(fieldToUpdate);
        return customerRepository.findById(customer.getCustomerId())
                .switchIfEmpty(notFound(customer.getCustomerId()))
                .doOnNext(found -> customerUpdater.update(customer, found))
                .flatMap(customerRepository::save)
                .map(customerEntityToCustomerMapper::map);
    }

    @Override
    public Mono<Void> deleteCustomer(Long id) {
        return customerRepository.deleteById(id);
    }

    @Override
    public Mono<Page<Customer>> listCustomers(PageRequest pageRequest) {
        return customerRepository
                .findAllByOrderByCustomerId(pageRequest)
                .map(customerEntityToCustomerMapper::map)
                .transform(collectPages(pageRequest, customerRepository.count()))
                .single();
    }

    @Override
    public Mono<Customer> findCustomerById(Long id) {
        return customerRepository
                .findById(id)
                .switchIfEmpty(notFound(id))
                .map(customerEntityToCustomerMapper::map);
    }

    private static Mono<CustomerEntity> notFound(Long id) {
        return Mono.error(new NotFoundException("Customer with id [%s] does not exist".formatted(id)));
    }
}
