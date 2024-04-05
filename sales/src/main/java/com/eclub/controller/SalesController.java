package com.eclub.controller;

import com.eclub.dto.SaleDto;
import com.eclub.mapper.SaleDtoToSellMessageMapper;
import com.eclub.queue.SalePublisher;
import com.eclub.queue.message.SellMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/sales")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SalesController {
    private final SaleDtoToSellMessageMapper saleDtoToSellMessageMapper;
    private final SalePublisher salePublisher;

    @PutMapping("/")
    public Mono<ResponseEntity<Void>> sell(@RequestBody SaleDto sale) {
        SellMessage sellMessage = saleDtoToSellMessageMapper.map(sale);
        return salePublisher.publish(sellMessage)
                .thenReturn(ResponseEntity.accepted().build());
    }
}
