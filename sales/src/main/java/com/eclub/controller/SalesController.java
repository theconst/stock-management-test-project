package com.eclub.controller;

import com.eclub.dto.SaleDto;
import com.eclub.mapper.SaleDtoToSellItemMapper;
import com.eclub.mapper.SaleDtoToSellMessageMapper;
import com.eclub.queue.SalePublisher;
import com.eclub.queue.message.RemoveFromStockMessage;
import com.eclub.service.SaleService;
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
    private final SaleDtoToSellItemMapper saleDtoToSellItemMapper;
    private final SalePublisher salePublisher;
    private final SaleService saleService;

    @PutMapping("/")
    public Mono<ResponseEntity<Void>> sell(@RequestBody SaleDto sale) {
        RemoveFromStockMessage sellMessage = saleDtoToSellMessageMapper.map(sale);
        //TODO(kkovalchuk): flaky
        return saleService.recordSale(saleDtoToSellItemMapper.map(sale))
                .then(salePublisher.publish(sellMessage))
                .thenReturn(ResponseEntity.accepted().build());
    }
}
