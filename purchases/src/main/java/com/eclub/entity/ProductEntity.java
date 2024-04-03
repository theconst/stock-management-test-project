package com.eclub.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class ProductEntity {
    @Id
    @Column(name = "product_id")
    long productId;

    String name;

    String vendor;

    String description;
}
