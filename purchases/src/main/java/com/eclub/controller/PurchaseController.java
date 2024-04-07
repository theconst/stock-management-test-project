package com.eclub.controller;

import com.eclub.dto.PurchaseDto;
import com.eclub.dto.StockOperationIdDto;
import com.eclub.queue.AddToStockPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("purchases")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PurchaseController {
    private final PurchaseDtoToAddToStockMessageMapper purchaseDtoToAddToStockMessageMapperMapper;
    private final AddToStockPublisher addToStockPublisher;

    @PutMapping("/")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<StockOperationIdDto> purchase(@RequestBody PurchaseDto purchase) {
        String messageId = UUID.randomUUID().toString();
        return addToStockPublisher
                .publish(purchaseDtoToAddToStockMessageMapperMapper.map(purchase, messageId))
                .thenReturn(new StockOperationIdDto(messageId));
    }
}
