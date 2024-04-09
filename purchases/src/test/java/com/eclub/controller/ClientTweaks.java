package com.eclub.controller;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClientConfigurer;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ClientTweaks {

    static WebTestClientConfigurer REST_HEADERS_CONF = (builder, httpHandlerBuilder, connector) ->
            builder.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString())
                    .build();
}
