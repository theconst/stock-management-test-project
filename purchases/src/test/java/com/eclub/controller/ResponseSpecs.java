package com.eclub.controller;

import com.eclub.domain.Product;
import com.eclub.domain.StockItem;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec.ResponseSpecConsumer;

import static org.hamcrest.Matchers.equalTo;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ResponseSpecs {

    static ResponseSpecConsumer productEqualTo(Product product) {
        return s -> s.expectBody()
                .jsonPath("$.name").value(equalTo(product.name()))
                .jsonPath("$.vendor").value(equalTo(product.vendor()))
                .jsonPath("$.description").value(equalTo(product.description()))
                .jsonPath("$.id").value(equalTo((int) product.id().id()));
    }

    static ResponseSpecConsumer stockEqualTo(StockItem stockItem) {
        var product = stockItem.product();
        return s -> s.expectBody()
                .jsonPath("$.product.name").value(equalTo(product.name()))
                .jsonPath("$.product.vendor").value(equalTo(product.vendor()))
                .jsonPath("$.product.description").value(equalTo(product.description()))
                .jsonPath("$.product.id").value(equalTo((int) product.id().id()))
                .jsonPath("$.batchNumber").value(equalTo((int) stockItem.batchNumber().batchNumber()))
                .jsonPath("$.id").value(equalTo((int) stockItem.id().id()));
    }
}
