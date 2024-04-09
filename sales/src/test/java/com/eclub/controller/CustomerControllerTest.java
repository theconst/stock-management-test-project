package com.eclub.controller;

import com.eclub.ControllerTest;
import com.eclub.common.DbTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.eclub.controller.ClientTweaks.REST_HEADERS_CONF;
import static com.eclub.controller.RestSpecs.BAD_REQUEST_REST_RESPONSE;
import static com.eclub.controller.RestSpecs.NOT_FOUND_REST_RESPONSE;
import static com.eclub.controller.RestSpecs.OK_REST_RESPONSE;
import static org.assertj.core.api.Assertions.assertThat;

@ControllerTest
class CustomerControllerTest {
    @Autowired
    DbTemplate db;
    @Autowired
    WebTestClient client;


    @Test
    void shouldCreateCustomerWhenFirstNameSpecified() {
        // @formatter:off
        client.mutateWith(REST_HEADERS_CONF).post().uri("/customers/")
                .bodyValue("""
                        {
                            "firstName" : "John",
                            "lastName" : "Smith",
                            "phoneNumber" : "+12345678",
                            "address" : "Green Str."
                        }
                        """)
                .exchange()
                .expectAll(OK_REST_RESPONSE)
                .expectBody()
                    .jsonPath("$.firstName").isEqualTo("John")
                    .jsonPath("$.lastName").isEqualTo("Smith")
                    .jsonPath("$.phoneNumber").isEqualTo("+12345678")
                    .jsonPath("$.address").isEqualTo("Green Str.");
        // @formatter:on
    }

    @Test
    void shouldReportInvalidRequestWhenCreatedWithBlankFirstName() {
        // @formatter:off
        client.mutateWith(REST_HEADERS_CONF).post().uri("/customers/")
                .bodyValue("""
                        {
                            "firstName" : "",
                            "lastName" : "Smith",
                            "phoneNumber" : "+12345678",
                            "address" : "Green Str."
                        }
                        """)
                .exchange()
                .expectAll(BAD_REQUEST_REST_RESPONSE)
                .expectBody()
                    .jsonPath("$.firstNameViolation").isEqualTo("First name is required");
        // @formatter:on
    }

    @Test
    void shouldReportInvalidRequestWhenCreatedWithBlankLastName() {
        // @formatter:off
        client.mutateWith(REST_HEADERS_CONF).post().uri("/customers/")
                .bodyValue("""
                        {
                            "firstName" : "John",
                            "lastName" : "",
                            "phoneNumber" : "+12345678",
                            "address" : "Green Str."
                        }
                        """)
                .exchange()
                .expectAll(BAD_REQUEST_REST_RESPONSE)
                .expectBody()
                    .jsonPath("$.lastNameViolation").isEqualTo("Last name is required");
        // @formatter:on
    }

    @Test
    void shouldModifyExistingCustomer() {
        db.prepareProductStockAndCustomerData();

        client.mutateWith(REST_HEADERS_CONF).put().uri("/customers/%s".formatted(1))
                .bodyValue("""
                        {
                            "address": "Wall Str."
                        }
                        """)
                .exchange()
                .expectAll(OK_REST_RESPONSE)
                .expectBody()
                    .jsonPath("$.firstName").isEqualTo("John")
                    .jsonPath("$.lastName").isEqualTo("Smith")
                    .jsonPath("$.phoneNumber").isEqualTo("+12345678")
                    .jsonPath("$.address").isEqualTo("Wall Str.");
    }

    @Test
    void shouldNotModifyNonExistingCustomer() {
        // @formatter:off
        client.mutateWith(REST_HEADERS_CONF).put().uri("/customers/%s".formatted(100500))
                .bodyValue("""
                        {
                            "address": "Wall Str."
                        }
                        """)
                .exchange()
                .expectAll(NOT_FOUND_REST_RESPONSE)
                .expectBody()
                    .jsonPath("$.error").isEqualTo("Customer with id [100500] does not exist");
        // @formatter:on
    }

    @Test
    void shouldReturnNotFoundWhenProductDoesNotExist() {
        // @formatter:off
        client.mutateWith(REST_HEADERS_CONF).get().uri("/customers/%s".formatted(1))
                .exchange()
                .expectAll(NOT_FOUND_REST_RESPONSE)
                .expectBody()
                .jsonPath("$.error").isEqualTo("Customer with id [1] does not exist");
        // @formatter:on
    }

    @Test
    void shouldReturnCustomerIfExists() {
        db.prepareProductStockAndCustomerData();

        client.mutateWith(REST_HEADERS_CONF).get().uri("/customers/%s".formatted(1))
                .exchange()
                .expectAll(OK_REST_RESPONSE)
                .expectBody()
                    .jsonPath("$.firstName").isEqualTo("John")
                    .jsonPath("$.lastName").isEqualTo("Smith")
                    .jsonPath("$.phoneNumber").isEqualTo("+12345678")
                    .jsonPath("$.address").isEqualTo("12345 Green Str.");;
    }

    @Test
    void shouldReturnEmptyPageIfNoItemsExist() {
        // @formatter:off
        client.mutateWith(REST_HEADERS_CONF).get().uri("/customers/")
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
    void shouldDeleteProductWhenProductExists() {
        db.prepareProductStockAndCustomerData();

        client.mutateWith(REST_HEADERS_CONF).delete().uri("/customers/%s".formatted(1))
                .exchange()
                .expectStatus().isOk()
                .expectBody().isEmpty();

        assertThat(db.findCustomerById(1L)).isNull();
    }

    @Test
    void shouldNotDeleteNothingIfProductDoesNotExist() {
        client.mutateWith(REST_HEADERS_CONF).delete().uri("/customers/%s".formatted(1))
                .exchange()
                .expectStatus().isOk()
                .expectBody().isEmpty();
    }
}