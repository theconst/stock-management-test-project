package com.eclub.repository;

import com.eclub.entity.StockOperationEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface StockOperationRepository extends ReactiveCrudRepository<StockOperationEntity, String> {
}
