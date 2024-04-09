package com.eclub.controller;

import com.eclub.ControllerTest;
import com.eclub.common.DbTemplate;
import com.eclub.domain.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec.ResponseSpecConsumer;

import static com.eclub.common.ProductStubs.IDEA_PAD;
import static com.eclub.common.ProductStubs.MACBOOK;
import static com.eclub.controller.ClientTweaks.REST_HEADERS_CONF;
import static com.eclub.controller.RestSpecs.BAD_REQUEST_REST_RESPONSE;
import static com.eclub.controller.RestSpecs.NOT_FOUND_REST_RESPONSE;
import static com.eclub.controller.RestSpecs.OK_REST_RESPONSE;
import static org.assertj.core.api.Fail.fail;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

@ControllerTest
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
class ProductControllerTest {
    @Autowired
    DbTemplate db;
    @Autowired
    WebTestClient client;

    @Test
    void shouldCreateProductWhenProductNameSpecified() {
        // @formatter:off
        client.mutateWith(REST_HEADERS_CONF).post().uri("/products")
                .bodyValue("""
                        {
                            "name" : "Lenovo IdeaPad",
                            "vendor" : "Lenovo",
                            "description" : "Cheap laptop"
                        }
                        """)
                .exchange()
                .expectAll(OK_REST_RESPONSE)
                .expectBody()
                    .jsonPath("$.name").value(equalTo("Lenovo IdeaPad"))
                    .jsonPath("$.vendor").value(equalTo("Lenovo"))
                    .jsonPath("$.description").value(equalTo("Cheap laptop"))
                    .jsonPath("$.id").exists();
        // @formatter:on
    }

    @Test
    void shouldReportInvalidRequestWhenCreatedWithBlankProductName() {
        // @formatter:off
        client.mutateWith(REST_HEADERS_CONF).post().uri("/products")
                .bodyValue("""
                    {
                        "name" : "",
                        "vendor" : "Apple",
                        "description" : "Expensive laptop"
                    }
                    """)
                .exchange()
                .expectAll(BAD_REQUEST_REST_RESPONSE)
                .expectBody()
                    .jsonPath("$.error", "Name must not be blank");
        // @formatter:on
    }

    @Test
    void shouldModifyExistingProduct() {
        db.insertProduct(IDEA_PAD);

        client.mutateWith(REST_HEADERS_CONF).put().uri("/products/%s".formatted(IDEA_PAD.id().id()))
                .bodyValue("""
                        {
                            "vendor": "Szengzen ltd."
                        }
                        """)
                .exchange()
                .expectAll(OK_REST_RESPONSE, productEqualTo(IDEA_PAD.toBuilder()
                        .vendor("Szengzen ltd.")
                        .build()));
    }

    @Test
    void shouldNotModifyNonExistingProduct() {
        // @formatter:off
        client.mutateWith(REST_HEADERS_CONF).put().uri("/products/%s".formatted(1))
                .bodyValue("""
                        {
                            "vendor": "Szengzen ltd."
                        }
                        """)
                .exchange()
                .expectAll(NOT_FOUND_REST_RESPONSE)
                .expectBody()
                    .jsonPath("$.error", "Item with id [1] does not exist");
        // @formatter:on
    }

    @Test
    void shouldReturnNotFoundWhenProductDoesNotExist() {
        // @formatter:off
        client.mutateWith(REST_HEADERS_CONF).get().uri("/products/%s".formatted(1))
                .exchange()
                .expectAll(NOT_FOUND_REST_RESPONSE)
                .expectBody()
                    .jsonPath("$.error", "Item with id [1] does not exist");
        // @formatter:on
    }

    @Test
    void shouldReturnProductIfExists() {
        db.insertProduct(MACBOOK);

        client.mutateWith(REST_HEADERS_CONF).get().uri("/products/%s".formatted(MACBOOK.id().id()))
                .exchange()
                .expectAll(OK_REST_RESPONSE, productEqualTo(MACBOOK));
    }

    @Test
    void shouldReturnEmptyPageIfNoItemsExist() {
        fail("Not implemented");
    }

    @Test
    void shouldPaginateThroughProducts() {
        fail("Not implemented");
    }

    static ResponseSpecConsumer productEqualTo(Product product) {
        return s -> s.expectBody()
                .jsonPath("$.name").value(equalTo(product.name()))
                .jsonPath("$.vendor").value(equalTo(product.vendor()))
                .jsonPath("$.description").value(equalTo(product.description()))
                .jsonPath("$.id").value(equalTo((int) product.id().id()));
    }
}