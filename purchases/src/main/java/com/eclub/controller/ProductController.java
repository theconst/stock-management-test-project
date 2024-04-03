package com.eclub.controller;

import com.eclub.dto.ProductDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("products")
public class ProductController {

    @GetMapping("/{id}")
    public Mono<ProductDto> getProductById(@PathVariable String id) {
        return Mono.just(new ProductDto(1, "Lenovo idea pad", "Lenovo", "Durable laptop"));
    }
}
