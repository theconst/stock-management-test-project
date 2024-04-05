package com.eclub.entity;

import lombok.Data;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Data
@Table("price")
public class PriceEntity {
    @Column("price_id")
    Long priceId;
    @Column("stock_item_id")
    Long stockItemId;

    BigDecimal price;
}
