package com.eclub.controller;

import com.eclub.dto.SaleDto;
import com.eclub.dto.StockOperationIdDto;
import com.eclub.mapper.SaleDtoToRemoveFromStockMapper;
import com.eclub.mapper.SaleDtoToSellItemMapper;
import com.eclub.queue.SalePublisher;
import com.eclub.message.RemoveFromStockMessage;
import com.eclub.service.SaleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/sales")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SalesController {
    private final SaleDtoToRemoveFromStockMapper saleDtoToRemoveFromStockMapper;
    private final SaleDtoToSellItemMapper saleDtoToSellItemMapper;
    private final SalePublisher salePublisher;
    private final SaleService saleService;

    @PutMapping("/")
    public Mono<StockOperationIdDto> sell(@RequestBody SaleDto sale) {
        RemoveFromStockMessage sellMessage = saleDtoToRemoveFromStockMapper.map(sale, UUID.randomUUID().toString());
        //TODO(kkovalchuk): this is not resilient in case of MQ fail
        return saleService.recordSale(saleDtoToSellItemMapper.map(sale))
                .then(salePublisher.publish(sellMessage))
                .thenReturn(new StockOperationIdDto(sellMessage.getMessageId()));
    }
}
