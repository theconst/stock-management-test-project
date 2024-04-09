package com.eclub.service;

import com.eclub.domain.Product;
import com.eclub.domain.Product.ProductId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import reactor.core.publisher.Mono;

public interface ProductService {

    Mono<Product> getProduct(ProductId id);

    Mono<Product> createProduct(Product product);

    Mono<Page<Product>> listProducts(PageRequest pageRequest);

    Mono<Product> updateProduct(Product map);
}
