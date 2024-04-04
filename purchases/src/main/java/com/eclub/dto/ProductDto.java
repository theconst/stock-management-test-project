package com.eclub.dto;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
public record ProductDto(Long id, String name, String vendor, String description) {
}
