package com.eclub.common;

import com.eclub.domain.Customer;
import com.eclub.domain.SaleItem;
import com.eclub.domain.StockItemId;

import java.math.BigDecimal;

public class SalesStubs {
    public static final SaleItem.SaleItemId SALE_ITEM_ID = new SaleItem.SaleItemId(1L);
    public static final SaleItem.SaleItemId SALE_ITEM_ID_2 = new SaleItem.SaleItemId(2L);
    public static final Customer.CustomerId CUSTOMER_ID = new Customer.CustomerId(1L);
    public static final StockItemId STOCK_ITEM_ID_1 = new StockItemId(1L);
    public static final BigDecimal PRICE = new BigDecimal(10).setScale(2);
    public static final int QUANTITY = 5;
    public static final SaleItem SALE_ITEM_1 = SaleItem.builder()
            .id(SALE_ITEM_ID)
            .customerId(CUSTOMER_ID)
            .stockItemId(STOCK_ITEM_ID_1)
            .price(PRICE)
            .quantity(QUANTITY)
            .build();
    public static final SaleItem SALE_ITEM_2 = SALE_ITEM_1.toBuilder()
                    .id(SALE_ITEM_ID_2)
                    .build();
}
