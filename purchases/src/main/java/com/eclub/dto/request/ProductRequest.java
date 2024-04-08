package com.eclub.dto.request;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
public record ProductRequest(Long id, String name, String vendor, String description) {
}
