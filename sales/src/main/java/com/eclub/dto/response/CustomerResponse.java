package com.eclub.dto.response;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
public record CustomerResponse(Long id, String firstName, String lastName, String phoneNumber, String address) {
}
