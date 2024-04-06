package com.eclub.controller;


import com.eclub.dto.PurchaseDto;
import com.eclub.queue.AddToStockPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("purchases")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PurchaseController {
    private final PurchaseDtoToPurchaseMessageMapper purchaseDtoToPurchaseMessageMapperMapper;
    private final AddToStockPublisher addToStockPublisher;

    @PutMapping("/")
    public Mono<ResponseEntity<Void>> purchase(@RequestBody PurchaseDto purchase) {
        return addToStockPublisher
                .publish(purchaseDtoToPurchaseMessageMapperMapper.map(purchase))
                .thenReturn(ResponseEntity.accepted().build());
    }
}
