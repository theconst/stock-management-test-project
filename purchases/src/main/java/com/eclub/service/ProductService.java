package com.eclub.service;

import com.eclub.domain.Price;
import com.eclub.domain.Product;
import com.eclub.domain.Product.ProductId;
import com.eclub.domain.StockItem.StockItemId;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {

    Mono<Product> getProduct(ProductId id);

    Mono<Product> createProduct(Product product);

    Mono<StockItemId> buyProduct(ProductId productId, Price sellingPrice, int quantity);

    Flux<Product> listStock();
}
