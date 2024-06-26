package com.eclub.controller;

import com.eclub.dto.request.PurchaseRequest;
import com.eclub.dto.response.StockOperationIdResponse;
import com.eclub.mapper.PurchaseRequestToAddToStockMessageMapper;
import com.eclub.queue.AddToStockPublisher;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("purchases")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PurchaseController {
    private final PurchaseRequestToAddToStockMessageMapper purchaseRequestToAddToStockMessageMapperMapper;
    private final AddToStockPublisher addToStockPublisher;

    @PutMapping("/")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "Adds product to stock. Modification currently is not supported")
    public Mono<StockOperationIdResponse> purchase(@Valid @RequestBody PurchaseRequest purchase) {
        String messageId = UUID.randomUUID().toString();
        return addToStockPublisher
                .publish(purchaseRequestToAddToStockMessageMapperMapper.map(purchase, messageId))
                .thenReturn(new StockOperationIdResponse(messageId));
    }
}
