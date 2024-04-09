package com.eclub.controller;

import com.eclub.ControllerTest;
import com.eclub.common.DbTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.eclub.common.ProductStubs.IDEA_PAD;
import static com.eclub.common.ProductStubs.MACBOOK;
import static com.eclub.controller.ClientTweaks.REST_HEADERS_CONF;
import static com.eclub.controller.ResponseSpecs.productEqualTo;
import static com.eclub.controller.RestSpecs.BAD_REQUEST_REST_RESPONSE;
import static com.eclub.controller.RestSpecs.NOT_FOUND_REST_RESPONSE;
import static com.eclub.controller.RestSpecs.OK_REST_RESPONSE;
import static org.assertj.core.api.Assertions.assertThat;

@ControllerTest
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
                    .jsonPath("$.name").isEqualTo("Lenovo IdeaPad")
                    .jsonPath("$.vendor").isEqualTo("Lenovo")
                    .jsonPath("$.description").isEqualTo("Cheap laptop")
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
                    .jsonPath("$.error").isEqualTo("Name must not be blank");
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
                    .jsonPath("$.error").isEqualTo("Product with id [1] does not exist");
        // @formatter:on
    }

    @Test
    void shouldReturnNotFoundWhenProductDoesNotExist() {
        // @formatter:off
        client.mutateWith(REST_HEADERS_CONF).get().uri("/products/%s".formatted(1))
                .exchange()
                .expectAll(NOT_FOUND_REST_RESPONSE)
                .expectBody()
                    .jsonPath("$.error").isEqualTo("Product with id [1] does not exist");
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
        // @formatter:off
        client.mutateWith(REST_HEADERS_CONF).get().uri("/products/")
                .exchange()
                .expectAll(OK_REST_RESPONSE)
                .expectBody()
                    .jsonPath("$.content").isEmpty()
                    .jsonPath("$.totalPages").isEqualTo(0)
                    .jsonPath("$.totalElements").isEqualTo(0)
                    .jsonPath("$.size").isEqualTo(20)
                    .jsonPath("$.last").isEqualTo(true)
                    .jsonPath("$.first").isEqualTo(true)
                    .jsonPath("$.number").isEqualTo(0);
        // @formatter:on
    }

    @Test
    void shouldPaginateThroughProducts() {
        db.insertProduct(IDEA_PAD);
        db.insertProduct(MACBOOK);

        // @formatter:off
        client.mutateWith(REST_HEADERS_CONF).get().uri("/products/?pageNumber=0&pageSize=1")
                .exchange()
                .expectAll(OK_REST_RESPONSE)
                .expectBody()
                    .jsonPath("$.content").isArray()
                    .jsonPath("$.content[0].id").isEqualTo(IDEA_PAD.id().id())
                    .jsonPath("$.content[0].name").isEqualTo(IDEA_PAD.name())
                    .jsonPath("$.content[0].vendor").isEqualTo(IDEA_PAD.vendor())
                    .jsonPath("$.content[0].description").isEqualTo(IDEA_PAD.description())

                    .jsonPath("$.totalPages").isEqualTo(2)
                    .jsonPath("$.totalElements").isEqualTo(2)
                    .jsonPath("$.size").isEqualTo(1)
                    .jsonPath("$.last").isEqualTo(false)
                    .jsonPath("$.first").isEqualTo(true)
                    .jsonPath("$.number").isEqualTo(0);
        client.mutateWith(REST_HEADERS_CONF).get().uri("/products/?pageNumber=1&pageSize=1")
                .exchange()
                .expectAll(OK_REST_RESPONSE)
                .expectBody()
                .jsonPath("$.content").isArray()
                .jsonPath("$.content[0].id").isEqualTo(MACBOOK.id().id())
                .jsonPath("$.content[0].name").isEqualTo(MACBOOK.name())
                .jsonPath("$.content[0].vendor").isEqualTo(MACBOOK.vendor())
                .jsonPath("$.content[0].description").isEqualTo(MACBOOK.description())

                .jsonPath("$.totalPages").isEqualTo(2)
                .jsonPath("$.totalElements").isEqualTo(2)
                .jsonPath("$.size").isEqualTo(1)
                .jsonPath("$.last").isEqualTo(true)
                .jsonPath("$.first").isEqualTo(false)
                .jsonPath("$.number").isEqualTo(1);
        // @formatter:on
    }

    @Test
    void shouldDeleteProductWhenProductExists() {
        db.insertProduct(MACBOOK);
        db.insertProduct(IDEA_PAD);

        client.mutateWith(REST_HEADERS_CONF).delete().uri("/products/%s".formatted(MACBOOK.id().id()))
                .exchange()
                .expectStatus().isOk()
                .expectBody().isEmpty();

        assertThat(db.selectProductByName(MACBOOK.name())).isNull();
        assertThat(db.selectProductByName(IDEA_PAD.name())).isNotNull();
    }

    @Test
    void shouldNotDeleteNothingIfProductDoesNotExist() {
        db.insertProduct(MACBOOK);

        client.mutateWith(REST_HEADERS_CONF).delete().uri("/products/%s".formatted(IDEA_PAD.id().id()))
                .exchange()
                .expectStatus().isOk()
                .expectBody().isEmpty();

        assertThat(db.selectProductByName(MACBOOK.name())).isNotEmpty();
    }

}