package com.eclub.controller;

import com.eclub.domain.RemoveFromStockOperationId;
import com.eclub.dto.SaleDto;
import com.eclub.dto.StockOperationIdDto;
import com.eclub.mapper.SaleDtoToSellItemMapper;
import com.eclub.service.SaleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/sales")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SalesController {
    private final SaleDtoToSellItemMapper saleDtoToSellItemMapper;
    private final SaleService saleService;

    @PutMapping("/")
    public Mono<StockOperationIdDto> sell(@RequestBody SaleDto sale) {
        return saleService.recordSale(saleDtoToSellItemMapper.map(sale))
                .map(RemoveFromStockOperationId::id)
                .map(StockOperationIdDto::new);
    }
}
