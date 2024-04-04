package com.eclub.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Table(name = "stock")
@Data
public class StockItemEntity {
    @Id
    @Column("stock_item_id")
    long stockItemId;

    BigDecimal sellingPrice;

    //Many To One
    @Column("product_id")
    long productId;
}
