package com.eclub.repository;

import com.eclub.entity.CustomerEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CustomerRepository extends ReactiveCrudRepository<CustomerEntity, Long> {
}
