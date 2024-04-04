package com.eclub.domain;


import lombok.Builder;

@Builder
public record Product(ProductId id, String name, String vendor, String description) {
    public record ProductId(long id) {}
}
