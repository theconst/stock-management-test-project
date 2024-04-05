package com.eclub.model;

import lombok.Builder;

@Builder
public record Customer(CustomerId id, String firstName, String lastName, String phoneNumber, String address) {
    public record CustomerId(Long id) {
    }
}
