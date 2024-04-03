package com.eclub.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class StockItemEntity {
    @Id
    @Column(name = "stock_item_id")
    long stockItemId;

    BigDecimal sellingPrice;

    @ManyToOne
    ProductEntity product;
}
