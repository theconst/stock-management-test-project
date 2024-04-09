package com.eclub.controller;

import com.eclub.ControllerTest;
import com.eclub.common.DbTemplate;
import com.eclub.domain.StockOperation.OperationId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.UUID;

import static com.eclub.common.ProductStubs.IDEA_PAD;
import static com.eclub.common.ProductStubs.MACBOOK;
import static com.eclub.common.StockStubs.IDEA_PAD_STOCK;
import static com.eclub.common.StockStubs.MACBOOK_STOCK;
import static com.eclub.common.StockStubs.QUANTITY;
import static com.eclub.controller.ClientTweaks.REST_HEADERS_CONF;
import static com.eclub.controller.ResponseSpecs.stockEqualTo;
import static com.eclub.controller.RestSpecs.OK_REST_RESPONSE;

@ControllerTest
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
                    .jsonPath("$.stockOperationId").isEqualTo(uuid.toString())
                    .jsonPath("$.status").isEqualTo("PENDING");
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
                    .jsonPath("$.stockOperationId").isEqualTo(uuid)
                    .jsonPath("$.status").isEqualTo("PROCESSED");
        // @formatter:on
    }

    @Test
    void shouldReturnEmptyPageIfNoStockItems() {
        // @formatter:off
        client.mutateWith(REST_HEADERS_CONF).get().uri("/stock-items/")
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
    void shouldReturnPageIfStockItemsPresent() {
        db.insertProduct(IDEA_PAD);
        db.insertProduct(MACBOOK);
        db.insertStock(IDEA_PAD_STOCK);
        db.insertStock(MACBOOK_STOCK);

        // @formatter:off
        client.mutateWith(REST_HEADERS_CONF).get().uri("/stock-items/?pageNumber=0&pageSize=1")
                .exchange()
                .expectAll(OK_REST_RESPONSE)
                .expectBody()
                .jsonPath("$.content").isArray()
                .jsonPath("$.content[0].batchNumber").isEqualTo(1)
                .jsonPath("$.content[0].quantity").isEqualTo(QUANTITY)
                .jsonPath("$.content[0].id").isEqualTo(1)
                .jsonPath("$.content[0].product.id").isEqualTo(IDEA_PAD.id().id())
                .jsonPath("$.content[0].product.name").isEqualTo(IDEA_PAD.name())
                .jsonPath("$.content[0].product.vendor").isEqualTo(IDEA_PAD.vendor())
                .jsonPath("$.content[0].product.description").isEqualTo(IDEA_PAD.description())

                .jsonPath("$.totalPages").isEqualTo(2)
                .jsonPath("$.totalElements").isEqualTo(2)
                .jsonPath("$.size").isEqualTo(1)
                .jsonPath("$.last").isEqualTo(false)
                .jsonPath("$.first").isEqualTo(true)
                .jsonPath("$.number").isEqualTo(0);
        client.mutateWith(REST_HEADERS_CONF).get().uri("/stock-items/?pageNumber=1&pageSize=1")
                .exchange()
                .expectAll(OK_REST_RESPONSE)
                .expectBody()
                .jsonPath("$.content").isArray()
                .jsonPath("$.content[0].id").isEqualTo(2)
                .jsonPath("$.content[0].batchNumber").isEqualTo(2)
                .jsonPath("$.content[0].quantity").isEqualTo(QUANTITY)
                .jsonPath("$.content[0].product.id").isEqualTo(MACBOOK.id().id())
                .jsonPath("$.content[0].product.name").isEqualTo(MACBOOK.name())
                .jsonPath("$.content[0].product.vendor").isEqualTo(MACBOOK.vendor())
                .jsonPath("$.content[0].product.description").isEqualTo(MACBOOK.description())

                .jsonPath("$.totalPages").isEqualTo(2)
                .jsonPath("$.totalElements").isEqualTo(2)
                .jsonPath("$.size").isEqualTo(1)
                .jsonPath("$.last").isEqualTo(true)
                .jsonPath("$.first").isEqualTo(false)
                .jsonPath("$.number").isEqualTo(1);
        // @formatter:on
    }
}