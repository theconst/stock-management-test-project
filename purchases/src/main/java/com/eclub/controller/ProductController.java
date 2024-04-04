package com.eclub.controller;

import com.eclub.dto.ProductDto;
import com.eclub.mapper.ProductDtoToProductMapper;
import com.eclub.mapper.ProductIdMapper;
import com.eclub.mapper.ProductToProductDtoMapper;
import com.eclub.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("products")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProductController {

    private final ProductService productService;
    private final ProductIdMapper productIdMapper;
    private final ProductDtoToProductMapper productDtoToProductMapper;
    private final ProductToProductDtoMapper productToProductDtoMapper;

    @GetMapping("/{id}")
    public Mono<ProductDto> getProductById(@PathVariable Long id) {
        return productService
                .getProduct(productIdMapper.map(id))
                .map(productToProductDtoMapper::map);
    }

    //TODO(kkovalchuk): pagination
    @GetMapping("/")
    public Flux<ProductDto> listAllProducts() {
        return productService
                .listStock()
                .map(productToProductDtoMapper::map);
    }

    @PostMapping
    public Mono<ProductDto> createProduct(ProductDto product) {
        return productService
                .createProduct(productDtoToProductMapper.map(product))
                .map(productToProductDtoMapper::map);
    }
}
