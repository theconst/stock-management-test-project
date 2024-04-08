package com.eclub.mapper;

import com.eclub.domain.SaleItem.SaleItemId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class SaleItemIdMapperTest {

    @Spy
    SaleItemIdMapper saleItemIdMapper;

    @Test
    void shouldMapNullToNullFromStockId() {
        assertThat(saleItemIdMapper.map((SaleItemId) null)).isNull();
    }

    @Test
    void shouldMapNullToNullFromLong() {
        assertThat(saleItemIdMapper.map((Long) null)).isNull();
    }

    @Test
    void shouldMapToLong() {
        assertThat(saleItemIdMapper.map(new SaleItemId(1L))).isEqualTo(1L);
    }

    @Test
    void shouldMapFromLong() {
        assertThat(saleItemIdMapper.map(1L)).isEqualTo(new SaleItemId(1L));
    }
}