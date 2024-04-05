package com.eclub.dto;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
public record CustomerDto(Long id, String firstName, String lastName, String phoneNumber, String address) {
}
