package com.eclub.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class ProductEntity {
    @Id
    long productId;

    String name;

    String vendor;

    String description;
}
