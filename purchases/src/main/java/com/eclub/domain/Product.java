package com.eclub.domain;


import lombok.Builder;

@Builder(toBuilder = true)
public record Product(ProductId id, String name, String vendor, String description) {
    public record ProductId(long id) {}
}
