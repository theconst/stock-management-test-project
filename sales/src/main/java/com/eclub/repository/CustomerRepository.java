package com.eclub.repository;

import com.eclub.entity.CustomerEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;


public interface CustomerRepository extends ReactiveCrudRepository<CustomerEntity, Long> {

    Flux<CustomerEntity> findAllByOrderByCustomerId(Pageable page);
}
