package com.eclub.queue;

import com.eclub.message.AddToStockMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AddToStockPublisher {
    private final RabbitTemplate rabbitTemplate;
    @Value("${stock-queue.routing-key}")
    private final String stockQueueRoutingKey;

    public Mono<Object> publish(AddToStockMessage addToStockMessage) {
        return Mono.fromRunnable(() -> {
                    log.info("Sending stock update {}[{}]", addToStockMessage, stockQueueRoutingKey);
                    rabbitTemplate.convertAndSend(stockQueueRoutingKey, addToStockMessage);
                })
                .subscribeOn(Schedulers.boundedElastic());
    }
}
