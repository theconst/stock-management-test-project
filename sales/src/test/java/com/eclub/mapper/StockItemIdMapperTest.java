package com.eclub.mapper;

import com.eclub.domain.StockItemId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class StockItemIdMapperTest {

    @Spy
    StockItemIdMapper stockItemIdMapper;

    @Test
    void shouldMapNullToNullFromStockId() {
        assertThat(stockItemIdMapper.map((StockItemId) null)).isNull();
    }

    @Test
    void shouldMapNullToNullFromLong() {
        assertThat(stockItemIdMapper.map((Long) null)).isNull();
    }

    @Test
    void shouldMapToLong() {
        assertThat(stockItemIdMapper.map(new StockItemId(1L))).isEqualTo(1L);
    }

    @Test
    void shouldMapFromLong() {
        assertThat(stockItemIdMapper.map(1L)).isEqualTo(new StockItemId(1L));
    }
}