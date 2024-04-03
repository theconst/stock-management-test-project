package com.eclub.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;

//TODO(kkovalchuk): refine model
@Builder
public record Product(ProductId id, String name, String vendor, String description) {

    @AllArgsConstructor(staticName = "of")
    public record ProductId(long id) {}
}
