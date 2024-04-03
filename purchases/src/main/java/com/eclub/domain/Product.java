package com.eclub.domain;


//TODO(kkovalchuk): refine model
public record Product(ProductId id, String name, String vendor, String description) {

    public record ProductId(long id) {}
}
