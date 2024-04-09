package com.eclub.common;

import com.eclub.domain.Product;
import com.eclub.domain.StockItem;
import com.eclub.domain.StockOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Common sql scripts for tests
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DbTemplate {

    private final DatabaseClient databaseClient;


    public void insertProduct(Product product) {
        databaseClient.sql("INSERT INTO product (product_id, name, vendor, description)"
                        + " VALUES (:id, :name, :vendor, :description)")
                .bind("id", product.id().id())
                .bind("name", product.name())
                .bind("vendor", product.vendor())
                .bind("description", product.description())
                .then()
                .block();
    }

    public Map<String, Object> selectProductByVendor(String vendor) {
        return databaseClient.sql("SELECT * FROM product WHERE vendor = :vendor")
                .bind("vendor", vendor)
                .fetch()
                .one()
                .block();
    }

    public Map<String, Object> selectProductByName(String name) {
        return databaseClient.sql("SELECT * FROM product WHERE name = :name")
                .bind("name", name)
                .fetch()
                .one()
                .block();
    }


    public void insertOperation(StockOperation.OperationId operationId) {
        databaseClient.sql(
                        "INSERT INTO stock_operation (operation_id, processed) VALUES (:operationId, CURRENT_TIMESTAMP())")
                .bind("operationId", operationId.id())
                .then()
                .block();
    }

    public void insertStock(StockItem stockItem) {
        databaseClient.sql("INSERT INTO stock (stock_item_id, product_id, batch_number, quantity)"
                        +" VALUES (:stock_item_id, :product_id, :batch_number, :quantity)")
                .bind("stock_item_id", stockItem.id().id())
                .bind("product_id", stockItem.product().id().id())
                .bind("batch_number", stockItem.batchNumber().batchNumber())
                .bind("quantity", stockItem.quantity())
                .then()
                .block();
    }
}
