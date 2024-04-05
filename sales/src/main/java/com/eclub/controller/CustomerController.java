package com.eclub.controller;

import com.eclub.dto.CustomerDto;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController("/customers")
public class CustomerController {

    //TODO(kkovalchuk): implement
    @GetMapping("/{id}")
    public Mono<CustomerDto> getCustomer() {
        return Mono.just(CustomerDto.builder().id(Long.valueOf(1L)).build());
    }

    @PutMapping("/{id}")
    public Mono<CustomerDto> modifyCustomer(@RequestBody CustomerDto customerDto) {
        return Mono.just(CustomerDto.builder().id(Long.valueOf(1L)).build());
    }

    @PostMapping("/")
    public Mono<CustomerDto> createCustomer(@RequestBody CustomerDto customerDto) {
        return Mono.just(CustomerDto.builder().id(Long.valueOf(1L)).build());
    }

    @GetMapping("/") //TODO: pagination
    public Flux<CustomerDto> listCustomers() {
        return getCustomer().flux();
    }

}
