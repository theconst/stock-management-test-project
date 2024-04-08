package com.eclub.dto.response;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
public record ProductResponse(Long id, String name, String vendor, String description) {
}
