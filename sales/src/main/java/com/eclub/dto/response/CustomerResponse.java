package com.eclub.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
public record CustomerResponse(
        @Schema(name = "Customer id") Long id,
        @Schema(name = "First name", example = "John") String firstName,
        @Schema(name = "Last name", example = "Smith") String lastName,
        @Schema(name = "Phone number", example = "+123456789") String phoneNumber,
        @Schema(name = "Address", description = "Customer address", example = "Green Str.") String address) {
}
