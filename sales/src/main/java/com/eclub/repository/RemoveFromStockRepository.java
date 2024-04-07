package com.eclub.repository;

import com.eclub.entity.RemoveFromStockEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface RemoveFromStockRepository extends ReactiveCrudRepository<RemoveFromStockEntity, String> {
}
