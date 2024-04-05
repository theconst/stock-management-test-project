package com.eclub.queue;

import com.eclub.dto.PurchaseDto;
import com.eclub.mapper.PurchaseDtoToPurchaseMapper;
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
    private final PurchaseDtoToPurchaseMapper purchaseDtoToPurchaseMapper;

    @RabbitListener(queues = "${purchase-queue.name:purchases}")
    //TODO(kkovalchuk): separate message for purchase
    public void updateStock(@Payload PurchaseDto purchase) {
        log.info("Updating stock with {}", purchase);
        stockService.purchase(purchaseDtoToPurchaseMapper.map(purchase)).block(Duration.ofSeconds(10));
    }
}
