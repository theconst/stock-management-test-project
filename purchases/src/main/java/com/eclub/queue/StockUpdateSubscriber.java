package com.eclub.queue;

import com.eclub.mapper.PurchaseMessageToPurchaseMapper;
import com.eclub.queue.message.PurchaseMessage;
import com.eclub.service.StockService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
class StockUpdateSubscriber {
    private final StockService stockService;
    private final PurchaseMessageToPurchaseMapper purchaseMessageToPurchaseMapper;

    @RabbitListener(queues = "${purchase-queue.name:purchases}")
    public void updateStock(@Payload PurchaseMessage purchase) {
        log.info("Updating stock with {}", purchase);
        //TODO: verify
        stockService.purchase(purchaseMessageToPurchaseMapper.map(purchase)).block(Duration.ofSeconds(10));
    }
}
