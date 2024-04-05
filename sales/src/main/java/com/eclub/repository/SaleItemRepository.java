package com.eclub.repository;

import com.eclub.entity.SaleItemEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface SaleItemRepository extends ReactiveCrudRepository<SaleItemEntity, Long> {
}
