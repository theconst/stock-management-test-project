package com.eclub.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Data
@Table("sale")
public class SaleItemEntity {
    @Id
    @Column("sale_id")
    Long saleId;
    @Column("customer_id")
    Long customerId;
    @Column("stock_item_id")
    Long stockItemId;

    BigDecimal price;

    Integer quantity;
}
