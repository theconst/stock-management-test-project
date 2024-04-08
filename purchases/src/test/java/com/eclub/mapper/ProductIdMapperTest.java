package com.eclub.mapper;

import com.eclub.domain.Product.ProductId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ProductIdMapperTest {

    @Spy
    ProductIdMapper productIdMapper;

    @Test
    void shouldMapNullToNullFromStockId() {
        assertThat(productIdMapper.map((ProductId) null)).isNull();
    }

    @Test
    void shouldMapNullToNullFromLong() {
        assertThat(productIdMapper.map((Long) null)).isNull();
    }

    @Test
    void shouldMapToLong() {
        assertThat(productIdMapper.map(new ProductId(1L))).isEqualTo(1L);
    }

    @Test
    void shouldMapFromLong() {
        assertThat(productIdMapper.map(1L)).isEqualTo(new ProductId(1L));
    }
}