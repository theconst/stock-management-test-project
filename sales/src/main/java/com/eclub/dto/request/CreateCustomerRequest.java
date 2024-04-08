package com.eclub.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
public record CreateCustomerRequest(
        @Schema(name = "First name", example = "John") String firstName,
        @Schema(name = "Last name", example = "Smith") String lastName,
        @Schema(name = "Phone number", example = "+123456789") String phoneNumber,
        @Schema(name = "Address", description = "Customer address", example = "Green Str.") String address) {
}
