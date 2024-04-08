package com.eclub.dto.request;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
public record CustomerRequest(Long id, String firstName, String lastName, String phoneNumber, String address) {
}
