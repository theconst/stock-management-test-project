package com.eclub.repository;

import com.eclub.entity.StockItemEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface StockRepository extends ReactiveCrudRepository<StockItemEntity, Long> {
    Mono<StockItemEntity> findByBatchNumberAndProductId(Long batchNumber, Long productId);

    Flux<StockItemEntity> findAllByOrderByStockItemId(PageRequest pageRequest);
}
