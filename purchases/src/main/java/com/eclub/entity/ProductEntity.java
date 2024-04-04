package com.eclub.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "product")
@Data
public class ProductEntity {
    @Id
    @Column("product_id")
    Long productId;

    String name;

    String vendor;

    String description;
}
