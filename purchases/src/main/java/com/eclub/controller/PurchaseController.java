package com.eclub.controller;


import com.eclub.dto.PurchaseDto;
import com.eclub.queue.PurchasePublisher;
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
    private final PurchasePublisher purchasePublisher;


    //TODO(kkovalchuk): move to separate controller?
    @PutMapping("/")
    public Mono<ResponseEntity<Void>> purchase(@RequestBody PurchaseDto purchase) {
        return purchasePublisher
                .publish(purchaseDtoToPurchaseMessageMapperMapper.map(purchase))
                .thenReturn(ResponseEntity.accepted().build());
    }
}
