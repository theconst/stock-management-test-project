package com.eclub.dto;

import lombok.Builder;

@Builder
public record ProductDto(long id, String name, String vendor, String description) {
}
