package com.eclub.controller;

import com.eclub.ControllerTest;
import com.eclub.common.DbTemplate;
import com.eclub.common.ProductStubs;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClientConfigurer;

import static com.eclub.controller.RestSpecs.BAD_REQUEST_REST_RESPONSE;
import static com.eclub.controller.RestSpecs.OK_REST_RESPONSE;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

@ControllerTest
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
class ProductControllerTest {
    static WebTestClientConfigurer PRODUCT_REST_REQUEST_CONF = (builder, httpHandlerBuilder, connector) ->
            builder.baseUrl("/products")
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());


    @Autowired
    DbTemplate db;
    @Autowired
    WebTestClient client;


    @Test
    void shouldCreateProductWhenProductNameSpecified() {
        // @formatter:off
        client.post().uri("/products")
                .contentType(MediaType.APPLICATION_JSON)
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
        client.mutateWith(PRODUCT_REST_REQUEST_CONF).post()
                .contentType(MediaType.APPLICATION_JSON)
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
        db.insertProduct(ProductStubs.IDEA_PAD);

        // @formatter:off
        client.mutateWith(PRODUCT_REST_REQUEST_CONF).post()
                .bodyValue("""
                        {
                            "id": 1,
                            "vendor": "Szengzen ltd."
                        }
                        """)
                .exchange()
                .expectAll(OK_REST_RESPONSE)
                .expectBody()
                    .jsonPath("$.name").value(equalTo("Lenovo IdeaPad"))
                    .jsonPath("$.vendor").value(equalTo("Szengzen ltd."))
                    .jsonPath("$.description").value(equalTo("Cheap laptop"))
                    .jsonPath("$.id").value(equalTo(1));
        // @formatter:on
    }

    @Test
    void shouldNotModifyNonExistingProduct() {
        // @formatter:off
        client.mutateWith(PRODUCT_REST_REQUEST_CONF).post()
                .bodyValue("""
                        {
                            "id": 1,
                            "vendor": "Szengzen ltd."
                        }
                        """)
                .exchange()
                .expectAll(BAD_REQUEST_REST_RESPONSE)
                .expectBody()
                    .jsonPath("$.error", "Item with id [1] does not exist");
        // @formatter:on
    }
}