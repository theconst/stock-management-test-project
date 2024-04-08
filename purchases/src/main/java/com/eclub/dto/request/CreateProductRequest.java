package com.eclub.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
public record CreateProductRequest(
        @Schema(description = "Product name", example = "Lenovo IdeaPad") String name,
        @Schema(description = "Vendor name", example = "Lenovo") String vendor,
        @Schema(description = "Product description", example = "Cheap laptop") String description) {
}
