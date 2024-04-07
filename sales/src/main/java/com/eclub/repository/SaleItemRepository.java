package com.eclub.repository;

import com.eclub.domain.SaleItem;
import com.eclub.entity.SaleItemEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SaleItemRepository extends ReactiveCrudRepository<SaleItemEntity, Long> {
    Flux<SaleItemEntity> findAllByOrderBySaleId(PageRequest pageRequest);
}
