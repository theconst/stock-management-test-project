package com.eclub.queue;

import com.eclub.queue.message.RemoveFromStockMessage;
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
public class SalePublisher {
    private final RabbitTemplate rabbitTemplate;
    @Value("${stock-queue.routing-key}")
    private final String purchaseRoutingKey;
    public Mono<Object> publish(RemoveFromStockMessage sale) {
        return Mono.fromRunnable(() -> {
                    log.info("Sending stock update {}[{}]", sale, purchaseRoutingKey);
                    rabbitTemplate.convertAndSend(purchaseRoutingKey, sale);
                })
                .subscribeOn(Schedulers.boundedElastic());
    }
}
