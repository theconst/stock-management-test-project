package com.eclub.controller;

import com.eclub.dto.request.CreateProductRequest;
import com.eclub.dto.request.ModifyProductRequest;
import com.eclub.dto.response.ProductResponse;
import com.eclub.mapper.CreateProductRequestToProductMapper;
import com.eclub.mapper.ModifyProductRequestToProductMapper;
import com.eclub.mapper.ProductIdMapper;
import com.eclub.mapper.ProductToProductResponseMapper;
import com.eclub.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("products")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductController {
    private final ProductService productService;

    private final ProductIdMapper productIdMapper;
    private final CreateProductRequestToProductMapper createProductRequestToProductMapper;
    private final ModifyProductRequestToProductMapper modifyProductRequestToProductMapper;
    private final ProductToProductResponseMapper productToProductResponseMapper;

    @GetMapping("/{id}")
    @Operation(summary = "Get product by id")
    public Mono<ProductResponse> getProductById(@PathVariable long id) {
        return productService
                .getProduct(productIdMapper.map(id))
                .map(productToProductResponseMapper::map);
    }

    @GetMapping("/")
    @Operation(summary = "List all products (pagination is done by id)")
    public Mono<Page<ProductResponse>> listAllProducts(
            @RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "20") int pageSize) {
        return productService
                .listProducts(PageRequest.of(pageNumber, pageSize))
                .map(page -> page.map(productToProductResponseMapper::map));
    }

    @PostMapping
    @Operation(summary = "Create product")
    public Mono<ProductResponse> createProduct(@Valid @RequestBody CreateProductRequest product) {
        return productService
                .createProduct(createProductRequestToProductMapper.map(product))
                .map(productToProductResponseMapper::map);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modify product")
    public Mono<ProductResponse> modifyProduct(
            @PathVariable("id") long id,
            @Valid @RequestBody ModifyProductRequest product) {
        return productService
                .updateProduct(modifyProductRequestToProductMapper.map(product, id))
                .map(productToProductResponseMapper::map);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Void> deleteProduct(@PathVariable("id") long id) {
        return productService.deleteProduct(productIdMapper.map(id));
    }

}
