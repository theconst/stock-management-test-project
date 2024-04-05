package com.eclub.entity;

import lombok.Data;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Data
@Table("sale")
public class SaleItemEntity {
    @Column("sale_id")
    Long saleId;
    @Column("customer_id")
    Long customerId;
    @Column("product_id")
    Long productId;

    BigDecimal price;

    Integer quantity;
}
