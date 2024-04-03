package com.eclub.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
public record Product(ProductId id, String name, String vendor, String description) {

    @AllArgsConstructor(staticName = "of")
    public record ProductId(long id) {}
}
