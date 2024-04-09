package com.eclub.controller;

import com.eclub.ControllerTest;
import com.eclub.common.DbTemplate;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.eclub.common.ProductStubs.IDEA_PAD;
import static com.eclub.common.ProductStubs.MACBOOK;
import static com.eclub.controller.ClientTweaks.REST_HEADERS_CONF;
import static com.eclub.controller.ResponseSpecs.productEqualTo;
import static com.eclub.controller.RestSpecs.BAD_REQUEST_REST_RESPONSE;
import static com.eclub.controller.RestSpecs.NOT_FOUND_REST_RESPONSE;
import static com.eclub.controller.RestSpecs.OK_REST_RESPONSE;
import static org.assertj.core.api.Assertions.assertThat;
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
                    .jsonPath("$.error", "Product with id [1] does not exist");
        // @formatter:on
    }

    @Test
    void shouldReturnNotFoundWhenProductDoesNotExist() {
        // @formatter:off
        client.mutateWith(REST_HEADERS_CONF).get().uri("/products/%s".formatted(1))
                .exchange()
                .expectAll(NOT_FOUND_REST_RESPONSE)
                .expectBody()
                    .jsonPath("$.error", "Product with id [1] does not exist");
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
    @Disabled
    void shouldReturnEmptyPageIfNoItemsExist() {
        fail("Not implemented");
    }

    @Test
    @Disabled
    void shouldPaginateThroughProducts() {
        fail("Not implemented");
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