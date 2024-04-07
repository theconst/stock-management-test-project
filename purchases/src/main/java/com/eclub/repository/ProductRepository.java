package com.eclub.repository;

import com.eclub.entity.ProductEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ProductRepository extends ReactiveCrudRepository<ProductEntity, Long> {
    Flux<ProductEntity> findAllByOrderByProductId(PageRequest pageRequest);
}
