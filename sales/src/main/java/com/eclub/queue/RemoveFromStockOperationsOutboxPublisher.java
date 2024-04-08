package com.eclub.queue;

import com.eclub.entity.RemoveFromStockEntity;
import com.eclub.mapper.RemoveFromStockEntityToRemoveFromStockMessageMapper;
import com.eclub.message.RemoveFromStockMessage;
import com.eclub.queue.RemoveFromStockPublisher;
import com.eclub.repository.RemoveFromStockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

/**
 * Published scheduled messages from outbox
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RemoveFromStockOperationsOutboxPublisher {
    private final RemoveFromStockPublisher removeFromStockPublisher;
    private final RemoveFromStockRepository removeFromStockRepository;
    private final RemoveFromStockEntityToRemoveFromStockMessageMapper
            removeFromStockEntityToRemoveFromStockMessageMapper;

    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.SECONDS)
    public void consumeRemoveFromStockOperationsFromOutbox() {
        removeFromStockRepository.findAll()
                .doOnNext(item -> log.info("Sending remove from stock [{}]", item))
                .flatMap(this::publishAndDelete)
                .blockLast();
    }

    @Transactional
    private Mono<Void> publishAndDelete(RemoveFromStockEntity removeFromStockMessage) {
        return removeFromStockPublisher.publish(mapToMessage(removeFromStockMessage))
                .then(removeFromStockRepository.delete(removeFromStockMessage));
    }

    private RemoveFromStockMessage mapToMessage(RemoveFromStockEntity removeFromStockMessage) {
        return removeFromStockEntityToRemoveFromStockMessageMapper.map(removeFromStockMessage);
    }
}
