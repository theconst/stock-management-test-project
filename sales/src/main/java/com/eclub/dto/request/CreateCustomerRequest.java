package com.eclub.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
public record CreateCustomerRequest(
        @NotBlank
        @Schema(description = "First name", example = "John") String firstName,
        @NotBlank
        @Schema(description = "Last name", example = "Smith") String lastName,
        @Schema(description = "Phone number", example = "+123456789") String phoneNumber,
        @Schema(description = "Customer address", example = "Green Str.") String address) {
}
