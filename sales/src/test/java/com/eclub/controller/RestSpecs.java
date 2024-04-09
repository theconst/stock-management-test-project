package com.eclub.controller;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec.ResponseSpecConsumer;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RestSpecs {

    static ResponseSpecConsumer OK_REST_RESPONSE = spec -> spec.expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON);
    static ResponseSpecConsumer BAD_REQUEST_REST_RESPONSE = spec -> spec.expectStatus().isBadRequest()
            .expectHeader().contentType(MediaType.APPLICATION_JSON);
    static ResponseSpecConsumer NOT_FOUND_REST_RESPONSE = spec -> spec.expectStatus().isNotFound()
            .expectHeader().contentType(MediaType.APPLICATION_JSON);
}
