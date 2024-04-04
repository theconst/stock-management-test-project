package com.eclub.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "stock")
@Data
public class StockItemEntity {
    @Id
    @Column("stock_item_id")
    Long stockItemId;

    Long batchNumber;

    Integer quantity;

    //Many To One
    @Column("product_id")
    Long productId;
}
