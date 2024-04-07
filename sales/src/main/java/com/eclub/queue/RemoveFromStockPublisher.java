package com.eclub.queue;

import com.eclub.message.RemoveFromStockMessage;
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
public class RemoveFromStockPublisher {
    private final RabbitTemplate rabbitTemplate;
    @Value("${stock-queue.routing-key}")
    private final String purchaseRoutingKey;

    /**
     * Publish remove from stock operation
     *
     * @param removeFromStockMessage message to publish
     * @return message of the id sent
     */
    public Mono<String> publish(RemoveFromStockMessage removeFromStockMessage) {
        return Mono.fromCallable(() -> {
                    log.info("Sending stock update {}[{}]", removeFromStockMessage, purchaseRoutingKey);
                    rabbitTemplate.convertAndSend(purchaseRoutingKey, removeFromStockMessage);

                    return removeFromStockMessage.getMessageId();
                })
                .subscribeOn(Schedulers.boundedElastic());
    }
}
