package com.eclub.service;

import com.eclub.ServiceTest;
import com.eclub.common.DbTemplate;
import com.eclub.domain.RemoveFromStockOperationId;
import com.eclub.domain.SaleItem;
import com.eclub.domain.SaleItemAndStockOperationId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.util.Map;

import static com.eclub.common.SalesStubs.QUANTITY;
import static com.eclub.common.SalesStubs.SALE_ITEM_1;
import static com.eclub.common.SalesStubs.SALE_ITEM_2;
import static com.eclub.common.SalesStubs.SALE_ITEM_ID;
import static com.eclub.common.SalesStubs.STOCK_ITEM_ID_1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ServiceTest
class SaleServiceTest {

    @Autowired
    DbTemplate db;
    @Autowired
    SaleService saleService;

    @Test
    void shouldListSales() {
        db.prepareProductStockAndCustomerData();
        db.insertSale(SALE_ITEM_1);
        db.insertSale(SALE_ITEM_2);

        var page1 = saleService.listSales(PageRequest.of(0, 1)).block();
        var page2 = saleService.listSales(PageRequest.of(1, 1)).block();

        assertThat(page1.getTotalPages()).isEqualTo(2);
        assertThat(page1).hasSize(1);
        assertThat(page1).containsExactly(SALE_ITEM_1);

        assertThat(page1.getTotalPages()).isEqualTo(2);
        assertThat(page2).hasSize(1);
        assertThat(page2).containsExactly(SALE_ITEM_2);

    }

    @Test
    void shouldRecordNewSaleAndStockOperation() {
        db.prepareProductStockAndCustomerData();

        SaleItemAndStockOperationId result = saleService.recordSale(SALE_ITEM_1.toBuilder().id(null).build()).block();
        RemoveFromStockOperationId operationId = result.stockOperationId();

        assertThat(result.sale()).isEqualTo(SALE_ITEM_1);
        assertThat(operationId).isNotNull();

        assertThat(db.getRemoveFromStockOutbox()).containsExactly(Map.of(
                "OPERATION_ID", operationId.id(),
                "STOCK_ITEM_ID", STOCK_ITEM_ID_1.id(),
                "QUANTITY", Long.valueOf(QUANTITY)
        ));
    }

    @Test
    void shouldNotAllowToUpdateSales() {
        db.prepareProductStockAndCustomerData();
        db.insertSale(SALE_ITEM_1);

        assertThrows(UnsupportedOperationException.class,
                saleService.recordSale(SALE_ITEM_1.toBuilder().quantity(1).build())::block);
    }

    @Test
    void shouldFindSaleById() {
        db.prepareProductStockAndCustomerData();
        db.insertSale(SALE_ITEM_1);

        SaleItem item = saleService.findSaleById(SALE_ITEM_ID).block();

        assertThat(item).isEqualTo(SALE_ITEM_1);
    }
}