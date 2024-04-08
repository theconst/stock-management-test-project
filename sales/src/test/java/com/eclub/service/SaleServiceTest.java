package com.eclub.service;

import com.eclub.ServiceTest;
import com.eclub.domain.Customer.CustomerId;
import com.eclub.domain.RemoveFromStockOperationId;
import com.eclub.domain.SaleItem;
import com.eclub.domain.SaleItem.SaleItemId;
import com.eclub.domain.SaleItemAndStockOperationId;
import com.eclub.domain.StockItemId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.r2dbc.core.DatabaseClient.GenericExecuteSpec;
import org.springframework.test.annotation.DirtiesContext;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

@ServiceTest
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
class SaleServiceTest {
    static final SaleItemId SALE_ITEM_ID = new SaleItemId(1L);
    static final CustomerId CUSTOMER_ID = new CustomerId(1L);
    static final StockItemId STOCK_ITEM_ID = new StockItemId(1L);
    static final BigDecimal PRICE = new BigDecimal(10).setScale(2);
    static final int QUANTITY = 5;
    static final SaleItem SALE_ITEM = SaleItem.builder()
            .id(SALE_ITEM_ID)
            .customerId(CUSTOMER_ID)
            .stockItemId(STOCK_ITEM_ID)
            .price(PRICE)
            .quantity(QUANTITY)
            .build();

    @Autowired DatabaseClient databaseClient;
    @Autowired SaleService saleService;

    @Test
    void shouldListSales() {
        fail("Not implemented");
    }

    @Test
    void shouldRecordNewSaleAndStockOperation() {
        prepareProductStockAndCustomerData();

        SaleItemAndStockOperationId result = saleService.recordSale(SALE_ITEM.toBuilder().id(null).build()).block();
        RemoveFromStockOperationId operationId = result.stockOperationId();

        assertThat(result.sale()).isEqualTo(SALE_ITEM);
        assertThat(operationId).isNotNull();

        assertThat(getOutbox()).containsExactly(Map.of(
                "OPERATION_ID", operationId.id(),
                "STOCK_ITEM_ID", STOCK_ITEM_ID.id(),
                "QUANTITY", Long.valueOf(QUANTITY)
        ));
    }

    @Test
    void shouldNotAllowToUpdateSales() {
        prepareProductStockAndCustomerData();
        insertSale(SALE_ITEM);

        assertThrows(UnsupportedOperationException.class,
                saleService.recordSale(SALE_ITEM.toBuilder().quantity(1).build())::block);
    }

    @Test
    void shouldFindSaleById() {
        prepareProductStockAndCustomerData();
        insertSale(SALE_ITEM);

        SaleItem item = saleService.findSaleById(SALE_ITEM_ID).block();

        assertThat(item).isEqualTo(SALE_ITEM);
    }

    private void insertSale(SaleItem saleItem) {
        databaseClient.sql("""
                INSERT INTO sale (sale_id, customer_id, stock_item_id, price, quantity)
                VALUES (:sale_id, :customer_id, :stock_item_id, :price, :quantity)
                """)
                .bind("sale_id", saleItem.id().id())
                .bind("customer_id", saleItem.customerId().id())
                .bind("stock_item_id", saleItem.stockItemId().id())
                .bind("price", saleItem.price())
                .bind("quantity", saleItem.quantity())
                .then()
                .block();
    }

    void prepareProductStockAndCustomerData() {
        Flux.just("""
                                INSERT INTO product (product_id, name, vendor, description)
                                VALUES (1, 'Lenovo IdeaPad', 'Lenovo', 'Cheap Laptop')""",
                        """
                                INSERT INTO stock (stock_item_id, product_id, batch_number, quantity)
                                VALUES (1, 1, 1, 10)
                                """,
                        """
                                INSERT INTO customer (customer_id, first_name, last_name, phone_number, address)
                                VALUES (1, 'John', 'Smith', '+12345678', '12345 Green Str.')
                                """)
                .map(databaseClient::sql)
                .flatMap(GenericExecuteSpec::then)
                .blockLast();
    }

    List<Map<String, Object>> getOutbox() {
        return databaseClient.sql("SELECT * FROM remove_from_stock_outbox")
                .fetch()
                .all()
                .collectList()
                .block();
    }
}