package com.eclub.controller;

import com.eclub.ControllerTest;
import com.eclub.common.DbTemplate;
import com.eclub.message.AddToStockMessage;
import com.eclub.queue.AddToStockPublisher;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static com.eclub.controller.ClientTweaks.REST_HEADERS_CONF;
import static com.eclub.controller.RestSpecs.BAD_REQUEST_REST_RESPONSE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ControllerTest
class PurchaseControllerTest {
    @Autowired
    DbTemplate db;
    @Autowired
    WebTestClient client;
    @Autowired
    AddToStockPublisher addToStockPublisher;

    @Test
    void shouldPublishAddToStockMessage() {
        when(addToStockPublisher.publish(any())).thenReturn(Mono.empty());
        // @formatter:off
        client.mutateWith(REST_HEADERS_CONF).put().uri("/purchases/")
                .bodyValue("""
                        {
                            "productId" : 1,
                            "batchNumber" : 12,
                            "quantity" : 100
                        }
                        """)
                .exchange()
                .expectStatus().isAccepted()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                    .jsonPath("$.stockOperationId").exists();
        // @formatter:on

        var addToStockMessage = ArgumentCaptor.forClass(AddToStockMessage.class);
        verify(addToStockPublisher).publish(addToStockMessage.capture());

        assertThat(addToStockMessage.getValue())
                .hasFieldOrProperty("messageId")
                .hasFieldOrPropertyWithValue("productId", 1L)
                .hasFieldOrPropertyWithValue("batchNumber", 12L)
                .hasFieldOrPropertyWithValue("quantity", 100);
    }

    @Test
    void shouldReturnBadRequestIfProductIdIsNull() {
        // @formatter:off
        client.mutateWith(REST_HEADERS_CONF).put().uri("/purchases/")
                .bodyValue("""
                        {
                            "batchNumber" : 12,
                            "quantity" : 100
                        }
                        """)
                .exchange()
                .expectAll(BAD_REQUEST_REST_RESPONSE)
                .expectBody()
                    .jsonPath("$.productIdViolation", "Product id is required");
        // @formatter:on

        verifyNoInteractions(addToStockPublisher);
    }


    @Test
    void shouldReturnBadRequestIfBatchNumberIsNull() {
        // @formatter:off
        client.mutateWith(REST_HEADERS_CONF).put().uri("/purchases/")
                .bodyValue("""
                        {
                            "productId" : 1,
                            "quantity" : 100
                        }
                        """)
                .exchange()
                .expectAll(BAD_REQUEST_REST_RESPONSE)
                .expectBody()
                .jsonPath("$.batchNumberViolation", "Batch number is required");
        // @formatter:on

        verifyNoInteractions(addToStockPublisher);
    }

    @Test
    void shouldReturnBadRequestIfQuantityIsNull() {
        // @formatter:off
        client.mutateWith(REST_HEADERS_CONF).put().uri("/purchases/")
                .bodyValue("""
                        {
                            "productId" : 1,
                            "batchNumber" : 12
                        }
                        """)
                .exchange()
                .expectAll(BAD_REQUEST_REST_RESPONSE)
                .expectBody()
                    .jsonPath("$.quantityViolation", "Quantity is required");
        // @formatter:on

        verifyNoInteractions(addToStockPublisher);
    }

}