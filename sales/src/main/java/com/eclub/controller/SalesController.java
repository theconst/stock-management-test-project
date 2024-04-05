package com.eclub.controller;

import com.eclub.dto.SaleDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController("/sales")
public class SalesController {
    //TODO(kkovalchuk): implement
    @PutMapping
    public Mono<ResponseEntity<Void>> sell(SaleDto sale) {
        return Mono.just(ResponseEntity.accepted().build());
    }
}
