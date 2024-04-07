package com.eclub.queue;

import com.eclub.mapper.StockTransactionMessageToStockOperationMapper;
import com.eclub.message.StockOperationMessage;
import com.eclub.service.StockService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
class StockUpdateListener {
    private final StockService stockService;
    private final StockTransactionMessageToStockOperationMapper stockTransactionMessageToStockOperationMapper;

    @RabbitListener(queues = "${stock-queue.name}")
    public void updateStock(@Payload StockOperationMessage transaction) {
        log.info("Updating stock with {}", transaction);
        stockService.update(stockTransactionMessageToStockOperationMapper.map(transaction)).block();
    }
}
