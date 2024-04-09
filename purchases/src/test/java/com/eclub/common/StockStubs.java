package com.eclub.common;

import com.eclub.domain.AddToStock;
import com.eclub.domain.RemoveFromStock;
import com.eclub.domain.StockItem;
import com.eclub.domain.StockOperation;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.eclub.common.ProductStubs.IDEA_PAD;
import static com.eclub.common.ProductStubs.MACBOOK;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StockStubs {
    public static final StockOperation.OperationId OPERATION_ID_1 = new StockOperation.OperationId("random-uuid");
    public static final StockItem.StockItemId STOCK_ITEM_ID_1 = new StockItem.StockItemId(1);
    public static final StockItem.StockItemId STOCK_ITEM_ID_2 = new StockItem.StockItemId(2);
    public static final StockItem.BatchNumber BATCH_NUMBER_1 = new StockItem.BatchNumber(1L);
    static final StockItem.BatchNumber BATCH_NUMBER_2 = new StockItem.BatchNumber(2);
    public static final int QUANTITY = 1;
    public static final StockOperation REMOVE_FROM_STOCK_TOO_MANY = RemoveFromStock.builder()
            .stockItemId(STOCK_ITEM_ID_1)
            .operationId(OPERATION_ID_1)
            .quantity(100 * QUANTITY)
            .build();
    public static final StockOperation REMOVE_FROM_STOCK = RemoveFromStock.builder()
            .stockItemId(STOCK_ITEM_ID_1)
            .operationId(OPERATION_ID_1)
            .quantity(QUANTITY)
            .build();
    public static final StockOperation ADD_TO_STOCK = AddToStock.builder()
            .operationId(OPERATION_ID_1)
            .productId(ProductStubs.PRODUCT_ID_1)
            .batchNumber(BATCH_NUMBER_1)
            .quantity(QUANTITY)
            .build();
    public static final StockItem MACBOOK_STOCK = StockItem.builder()
            .id(STOCK_ITEM_ID_2)
            .product(MACBOOK)
            .batchNumber(BATCH_NUMBER_2)
            .quantity(QUANTITY)
            .build();
    public static final StockItem IDEA_PAD_STOCK = StockItem.builder()
            .id(STOCK_ITEM_ID_1)
            .product(IDEA_PAD)
            .batchNumber(BATCH_NUMBER_1)
            .quantity(QUANTITY)
            .build();
}
