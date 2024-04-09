package com.eclub.controller;

import com.eclub.ControllerTest;
import com.eclub.common.DbTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.eclub.common.SalesStubs.SALE_ITEM_1;
import static com.eclub.controller.ClientTweaks.REST_HEADERS_CONF;
import static com.eclub.controller.RestSpecs.NOT_FOUND_REST_RESPONSE;
import static com.eclub.controller.RestSpecs.OK_REST_RESPONSE;
import static org.assertj.core.api.Assertions.assertThat;

@ControllerTest
class SalesControllerTest {
    @Autowired
    DbTemplate db;
    @Autowired
    WebTestClient client;

    @Test
    void shouldReportNotFoundIfSaleItemDoesNotExist() {
        // @formatter:off
        client.mutateWith(REST_HEADERS_CONF).get().uri("/sales/2")
                .exchange()
                .expectAll(NOT_FOUND_REST_RESPONSE)
                .expectBody()
                    .jsonPath("error").isEqualTo("Sale item [2] is not found");
        // @formatter:on
    }

    @Test
    void shouldFindSaleIfExits() {
        db.prepareProductStockAndCustomerData();
        db.insertSale(SALE_ITEM_1);

        // @formatter:off
        client.mutateWith(REST_HEADERS_CONF).get().uri("/sales/1")
                .exchange()
                .expectAll(OK_REST_RESPONSE)
                .expectBody()
                    .jsonPath("$.id").isEqualTo(SALE_ITEM_1.id().id())
                    .jsonPath("$.stockItemId").isEqualTo(SALE_ITEM_1.stockItemId().id())
                    .jsonPath("$.customerId").isEqualTo(SALE_ITEM_1.customerId().id())
                    .jsonPath("$.price").isEqualTo(SALE_ITEM_1.price().doubleValue())
                    .jsonPath("$.quantity").isEqualTo(SALE_ITEM_1.quantity());
        // @formatter:on
    }

    @Test
    void shouldRecordSale() {
        db.prepareProductStockAndCustomerData();

        // @formatter:off
        client.mutateWith(REST_HEADERS_CONF).put().uri("/sales/")
                .bodyValue("""
                    {
                        "stockItemId" : 1,
                        "customerId" : 1,
                        "price" : 100,
                        "quantity" : 10
                    }
                    """)
                .exchange()
                .expectStatus().isAccepted()
                .expectBody()
                    .jsonPath("$.stockOperationId").exists()
                    .jsonPath("$.sale.id").isEqualTo(1)
                    .jsonPath("$.sale.stockItemId").isEqualTo(1)
                    .jsonPath("$.sale.price").isEqualTo(100)
                    .jsonPath("$.sale.quantity").isEqualTo(10);
        // @formatter:on

        assertThat(db.getRemoveFromStockOutbox()).isNotEmpty();
    }
}