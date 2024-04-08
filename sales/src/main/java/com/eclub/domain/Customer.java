package com.eclub.domain;

import lombok.Builder;

@Builder(toBuilder = true)
public record Customer(CustomerId id, String firstName, String lastName, String phoneNumber, String address) {
    public record CustomerId(Long id) {
    }
}
