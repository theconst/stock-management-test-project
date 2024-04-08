package com.eclub.repository;

import com.eclub.entity.SaleItemEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface SaleItemRepository extends ReactiveCrudRepository<SaleItemEntity, Long> {
    Flux<SaleItemEntity> findAllByOrderBySaleId(PageRequest pageRequest);
}
