package com.eclub.controller;

import com.eclub.dto.ProductDto;
import com.eclub.mapper.ProductDtoToProductMapper;
import com.eclub.mapper.ProductIdMapper;
import com.eclub.mapper.ProductToProductDtoMapper;
import com.eclub.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("products")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
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

    @GetMapping("/")
    public Mono<Page<ProductDto>> listAllProducts(
            @RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "20") int pageSize) {
        return productService
                .listProducts(PageRequest.of(pageNumber, pageSize))
                .map(page -> page.map(productToProductDtoMapper::map));
    }

    @PostMapping
    public Mono<ProductDto> createProduct(@RequestBody ProductDto product) {
        return upsertProduct(product);
    }

    @PutMapping("/{id}")
    public Mono<ProductDto> modifyProduct(@RequestBody ProductDto product) {
        return upsertProduct(product);
    }

    private Mono<ProductDto> upsertProduct(ProductDto product) {
        return productService
                .upsertProduct(productDtoToProductMapper.map(product))
                .map(productToProductDtoMapper::map);
    }
}
