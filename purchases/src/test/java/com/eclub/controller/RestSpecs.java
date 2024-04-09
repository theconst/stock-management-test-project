package com.eclub.controller;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RestSpecs {

    static WebTestClient.ResponseSpec.ResponseSpecConsumer OK_REST_RESPONSE = spec -> spec.expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON);
    static WebTestClient.ResponseSpec.ResponseSpecConsumer BAD_REQUEST_REST_RESPONSE = spec -> spec.expectStatus().isBadRequest()
            .expectHeader().contentType(MediaType.APPLICATION_JSON);
}
