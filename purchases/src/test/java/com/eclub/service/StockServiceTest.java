package com.eclub.service;

import com.eclub.ServiceTest;
import com.eclub.common.DbTemplate;
import com.eclub.domain.StockItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;

import static com.eclub.common.ProductStubs.IDEA_PAD;
import static com.eclub.common.ProductStubs.MACBOOK;
import static com.eclub.common.StockStubs.ADD_TO_STOCK;
import static com.eclub.common.StockStubs.BATCH_NUMBER_1;
import static com.eclub.common.StockStubs.IDEA_PAD_STOCK;
import static com.eclub.common.StockStubs.MACBOOK_STOCK;
import static com.eclub.common.StockStubs.OPERATION_ID_1;
import static com.eclub.common.StockStubs.QUANTITY;
import static com.eclub.common.StockStubs.REMOVE_FROM_STOCK;
import static com.eclub.common.StockStubs.REMOVE_FROM_STOCK_TOO_MANY;
import static com.eclub.common.StockStubs.STOCK_ITEM_ID_1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_CLASS;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

@DirtiesContext(classMode = AFTER_CLASS)
class StockServiceTest {

    @Nested
    @ServiceTest
    @DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
    class StockOperationTest {
        @Autowired DbTemplate db;
        @Autowired StockService stockService;

        @Test
        void shouldReturnFalseIfOperationNotProcessed() {
            var isProcessed = stockService.isOperationProcessed(OPERATION_ID_1).block();

            assertThat(isProcessed).isFalse();
        }

        @Test
        void shouldReturnTrueIfOperationProcessed() {
            db.insertOperation(OPERATION_ID_1);

            var isProcessed = stockService.isOperationProcessed(OPERATION_ID_1).block();

            assertThat(isProcessed).isTrue();
        }

    }

    @Nested
    @ServiceTest
    @DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
    class StockListTest {
        @Autowired DbTemplate db;
        @Autowired StockService stockService;

        @BeforeEach
        void setUp() {
            db.insertProduct(IDEA_PAD);
            db.insertStock(IDEA_PAD_STOCK);

            db.insertProduct(MACBOOK);
            db.insertStock(MACBOOK_STOCK);
        }

        @Test
        void shouldGetStockItemById() {
            var stockItem = stockService.getStockItem(STOCK_ITEM_ID_1).block();

            assertThat(stockItem).isEqualTo(IDEA_PAD_STOCK);
        }

        @Test
        void shouldPaginateThroughStock() {
            var page1 = stockService.listStock(PageRequest.of(0, 1)).block();
            var page2 = stockService.listStock(PageRequest.of(1, 1)).block();

            assertThat(page1.getTotalPages()).isEqualTo(2);
            assertThat(page1).hasSize(1);
            assertThat(page1).containsExactly(IDEA_PAD_STOCK);

            assertThat(page1.getTotalPages()).isEqualTo(2);
            assertThat(page2).hasSize(1);
            assertThat(page2).containsExactly(MACBOOK_STOCK);
        }

    }

    @Nested
    @ServiceTest
    @DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
    class StockUpdateTest {
        @Autowired DbTemplate db;
        @Autowired StockService stockService;

        @Test
        void shouldCreateNewStockItemIfItemIsAddedToStockAndTheProductExists() {
            db.insertProduct(IDEA_PAD);

            StockItem stockItem = stockService.update(ADD_TO_STOCK).block();

            assertThat(stockItem).isEqualTo(
                    StockItem.builder()
                            .id(STOCK_ITEM_ID_1)
                            .product(IDEA_PAD)
                            .batchNumber(BATCH_NUMBER_1)
                            .quantity(QUANTITY)
                            .build()
            );
        }

        @Test
        void shouldAddToStockItemIfTheBatchAndProductAlreadyInStock() {
            db.insertProduct(IDEA_PAD);
            db.insertStock(IDEA_PAD_STOCK);

            StockItem stockItem = stockService.update(ADD_TO_STOCK).block();

            assertThat(stockItem).isEqualTo(
                    StockItem.builder()
                            .id(STOCK_ITEM_ID_1)
                            .product(IDEA_PAD)
                            .batchNumber(BATCH_NUMBER_1)
                            .quantity(2 * QUANTITY)
                            .build()
            );
        }

        @Test
        void shouldNotAddToStockIfOperationAlreadyCompleted() {
            db.insertProduct(IDEA_PAD);
            db.insertStock(IDEA_PAD_STOCK);
            db.insertOperation(OPERATION_ID_1);

            var stockItem = stockService.update(ADD_TO_STOCK).block();

            assertThat(stockItem).isEqualTo(
                    StockItem.builder()
                            .id(STOCK_ITEM_ID_1)
                            .product(IDEA_PAD)
                            .batchNumber(BATCH_NUMBER_1)
                            .quantity(QUANTITY)
                            .build()
            );
        }

        @Test
        void shouldReportErrorOnExpiredStockItem() {
            // operation recorded, but stock item was deleted at some point before
            db.insertOperation(OPERATION_ID_1);

            var error = assertThrows(IllegalStateException.class, stockService.update(ADD_TO_STOCK)::block);

            assertThat(error)
                    .hasMessageContaining("Stock item")
                    .hasMessageContaining("already unavailable");
        }

        @Test
        void shouldNotRemoveFromStockIfNoSuchItemsAvailable() {
            var error = assertThrows(IllegalStateException.class, stockService.update(REMOVE_FROM_STOCK)::block);

            assertThat(error)
                    .hasMessageContaining("Cannot sale non-existing stock item");
        }

        @Test
        void shouldRemoveFromStockIfSufficientItemsAvailableInStock() {
            db.insertProduct(IDEA_PAD);
            db.insertStock(IDEA_PAD_STOCK);

            var stockItem = stockService.update(REMOVE_FROM_STOCK).block();

            assertThat(stockItem).isEqualTo(
                    StockItem.builder()
                            .id(STOCK_ITEM_ID_1)
                            .product(IDEA_PAD)
                            .batchNumber(BATCH_NUMBER_1)
                            .quantity(0)
                            .build()
            );
        }

        @Test
        void shouldNotRemoveFromStockIfOperationAlreadyCompleted() {
            db.insertProduct(IDEA_PAD);
            db.insertStock(IDEA_PAD_STOCK);
            db.insertOperation(OPERATION_ID_1);

            var stockItem = stockService.update(ADD_TO_STOCK).block();

            assertThat(stockItem).isEqualTo(
                    StockItem.builder()
                            .id(STOCK_ITEM_ID_1)
                            .product(IDEA_PAD)
                            .batchNumber(BATCH_NUMBER_1)
                            .quantity(QUANTITY)
                            .build()
            );
        }

        @Test
        void shouldNotRemoveFromStockIfNonSufficientItemsInStock() {
            db.insertProduct(IDEA_PAD);
            db.insertStock(IDEA_PAD_STOCK);

            var error = assertThrows(IllegalArgumentException.class, stockService.update(REMOVE_FROM_STOCK_TOO_MANY)::block);

            assertThat(error).hasMessageContaining("Stock exhausted");
        }
    }
}