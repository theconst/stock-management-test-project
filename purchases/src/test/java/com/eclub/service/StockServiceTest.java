package com.eclub.service;

import com.eclub.ServiceTest;
import com.eclub.domain.AddToStock;
import com.eclub.domain.Product;
import com.eclub.domain.Product.ProductId;
import com.eclub.domain.StockItem;
import com.eclub.domain.StockItem.BatchNumber;
import com.eclub.domain.StockItem.StockItemId;
import com.eclub.domain.StockOperation;
import com.eclub.domain.StockOperation.OperationId;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;


@ServiceTest
class StockServiceTest {
    @Autowired DatabaseClient databaseClient;
    @Autowired StockService stockService;

    @Nested
    @DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
    class StockUpdateTest {
        static final OperationId OPERATION_ID_1 = new OperationId("random-uiid");
        static final ProductId PRODUCT_ID_1 = new ProductId(1L);
        static final StockItemId STOCK_ITEM_ID_1 = new StockItemId(1);
        static final BatchNumber BATCH_NUMBER_1 = new BatchNumber(1L);

        static final int QUANTITY = 1;

        static final Product IDEA_PAD = Product.builder()
                .id(PRODUCT_ID_1)
                .name("Lenovo IdeaPad")
                .vendor("Lenovo")
                .description("Cheap laptop")
                .build();

        static final StockItem IDEA_PAD_STOCK = StockItem.builder()
                .id(STOCK_ITEM_ID_1)
                .product(IDEA_PAD)
                .batchNumber(BATCH_NUMBER_1)
                .quantity(QUANTITY)
                .build();

        static final StockOperation ADD_TO_STOCK = AddToStock.builder()
                .operationId(OPERATION_ID_1)
                .productId(PRODUCT_ID_1)
                .batchNumber(BATCH_NUMBER_1)
                .quantity(QUANTITY)
                .build();

        @Test
        void shouldCreateNewStockItemIfItemIsAddedToStockAndTheProductExists() {
            insertProduct(IDEA_PAD);

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
            insertProduct(IDEA_PAD);
            insertStock(IDEA_PAD_STOCK);

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

        }

        @Test
        void shouldRemoveToStockIfOperationAlreadyCompleted() {

        }

        @Test
        void shouldNotRemoveFromStockIfOperationAlreadyCompleted() {
        }

        @Test
        void shouldNotRemoveFromStockIfNonSufficientItemsInStock() {

        }

        @Test
        void shouldRemoveFromStockIfSufficientItemsAvailableInStock() {
            
        }

        private void insertStockItem() {

        }


    }

    void insertStock(StockItem stockItem) {
        databaseClient.sql("INSERT INTO stock (stock_item_id, product_id, batch_number, quantity)"
            +" VALUES (:stock_item_id, :product_id, :batch_number, :quantity)")
                .bind("stock_item_id", stockItem.id().id())
                .bind("product_id", stockItem.product().id().id())
                .bind("batch_number", stockItem.batchNumber().batchNumber())
                .bind("quantity", stockItem.quantity())
                .then()
                .block();
    }

    void insertProduct(Product product) {
        databaseClient.sql("INSERT INTO product (product_id, name, vendor, description)"
                        + " VALUES (:id, :name, :vendor, :description)")
                .bind("id", product.id().id())
                .bind("name", product.name())
                .bind("vendor", product.vendor())
                .bind("description", product.description())
                .then()
                .block();
    }
}