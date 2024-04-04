package com.eclub.service;

import com.eclub.domain.Product;
import com.eclub.domain.Product.ProductId;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {

    Mono<Product> getProduct(ProductId id);

    Mono<Product> upsertProduct(Product product);

    Flux<Product> listProducts();
}
