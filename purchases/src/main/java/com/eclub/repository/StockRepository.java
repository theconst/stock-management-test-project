package com.eclub.repository;

import com.eclub.entity.StockItemEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface StockRepository extends ReactiveCrudRepository<StockItemEntity, Long> {
}
