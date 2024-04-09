package com.eclub.common;

import com.eclub.domain.SaleItem;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DbTemplate {

    private final DatabaseClient client;

    public void insertSale(SaleItem saleItem) {
        client.sql("""
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

    public void prepareProductStockAndCustomerData() {
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
                .map(client::sql)
                .flatMap(DatabaseClient.GenericExecuteSpec::then)
                .blockLast();
    }

    public Map<String, Object> findCustomerById(Long id) {
        return client.sql("SELECT * FROM customer WHERE customer_id = :id")
                .bind("id", id)
                .fetch()
                .one()
                .block();
    }

    public List<Map<String, Object>> getRemoveFromStockOutbox() {
        return client.sql("SELECT * FROM remove_from_stock_outbox")
                .fetch()
                .all()
                .collectList()
                .block();
    }
}
