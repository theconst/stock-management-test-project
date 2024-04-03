package com.eclub.dto;

import com.eclub.domain.Product;

public record ProductDto(long id, String name, String vendor, String description) {
}
