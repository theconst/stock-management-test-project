package com.eclub.service;

import com.eclub.domain.Product;
import com.eclub.domain.Product.ProductId;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {

    Mono<Void> createProduct(Product product);

    Mono<Void> buyProduct(ProductId productId, int quantity);

    Flux<Product> listStock();
}
