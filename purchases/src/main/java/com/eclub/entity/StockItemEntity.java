package com.eclub.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class StockItemEntity {
    @Id
    long stockItemId;

    BigDecimal sellingPrice;

    @ManyToOne
    ProductEntity product;
}
