package com.eclub.queue;

import com.eclub.mapper.StockTransactionMessageToStockOperationMapper;
import com.eclub.queue.message.StockTransactionMessage;
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
    private final StockTransactionMessageToStockOperationMapper stockTransactionMessageToStockOperationMapper;

    @RabbitListener(queues = "${stock-queue.name}")
    public void updateStock(@Payload StockTransactionMessage transaction) {
        log.info("Updating stock with {}", transaction);
        //TODO(kkovalchuk): verify
        stockService.update(stockTransactionMessageToStockOperationMapper.map(transaction)).block(Duration.ofSeconds(10));
    }
}
