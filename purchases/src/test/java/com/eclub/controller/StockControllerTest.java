package com.eclub.controller;

import com.eclub.ControllerTest;
import com.eclub.common.DbTemplate;
import com.eclub.domain.StockOperation.OperationId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.UUID;

import static com.eclub.common.ProductStubs.IDEA_PAD;
import static com.eclub.common.StockStubs.IDEA_PAD_STOCK;
import static com.eclub.controller.ClientTweaks.REST_HEADERS_CONF;
import static com.eclub.controller.ResponseSpecs.stockEqualTo;
import static com.eclub.controller.RestSpecs.OK_REST_RESPONSE;
import static org.assertj.core.api.BDDAssertions.fail;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

@ControllerTest
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
class StockControllerTest {
    @Autowired
    DbTemplate db;
    @Autowired
    WebTestClient client;

    @Test
    void shouldReturnNotFoundIfStockItemDoesNotExist() {
        // @formatter:off
        client.mutateWith(REST_HEADERS_CONF).get().uri("/stock-items/%s".formatted(1))
                .exchange()
                .expectAll(OK_REST_RESPONSE)
                .expectBody()
                    .jsonPath("$.error", "Stock item with id [%s] does not exist");
        // @formatter:on
    }

    @Test
    void shouldGetStockItemByIdIfExists() {
        db.insertProduct(IDEA_PAD);
        db.insertStock(IDEA_PAD_STOCK);

        client.mutateWith(REST_HEADERS_CONF).get().uri("/stock-items/%s".formatted(IDEA_PAD_STOCK.id().id()))
                .exchange()
                .expectAll(OK_REST_RESPONSE, stockEqualTo(IDEA_PAD_STOCK));
    }

    @Test
    void shouldGetStockOperationAsPendingIfNotInDatabase() {
        UUID uuid = UUID.randomUUID();
        // @formatter:off
        client.mutateWith(REST_HEADERS_CONF).get().uri("/stock-items/operations/%s/status".formatted(uuid))
                .exchange()
                .expectAll(OK_REST_RESPONSE)
                .expectBody()
                    .jsonPath("$.stockOperationId").value(equalTo(uuid.toString()))
                    .jsonPath("$.status").value(equalTo("PENDING"));
        // @formatter:on
    }

    @Test
    void shouldGetStockOperationAsCompleteIfInDatabase() {
        String uuid = UUID.randomUUID().toString();
        db.insertOperation(new OperationId(uuid));

        // @formatter:off
        client.mutateWith(REST_HEADERS_CONF).get().uri("/stock-items/operations/%s/status".formatted(uuid))
                .exchange()
                .expectAll(OK_REST_RESPONSE)
                .expectBody()
                    .jsonPath("$.stockOperationId").value(equalTo(uuid))
                    .jsonPath("$.status").value(equalTo("PROCESSED"));
        // @formatter:on
    }

    @Test
    void shouldReturnEmptyPageIfNoStockItems() {
        fail("Not implemented");
    }

    @Test
    void shouldReturnPageIfStockItemsPresent() {
        fail("Not implemented");
    }
}