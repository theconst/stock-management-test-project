package com.eclub.domain;

import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor(staticName = "of")
public record Price(BigDecimal price) {
}
