package com.eclub.dto;

import lombok.Builder;

@Builder
public record ProductDto(Long id, String name, String vendor, String description) {
}
