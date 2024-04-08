package com.eclub.mapper;

import com.eclub.domain.StockItem.BatchNumber;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class BatchNumberMapperTest {

    @Spy
    BatchNumberMapper batchNumberMapper;

    @Test
    void shouldMapNullToNullFromStockId() {
        assertThat(batchNumberMapper.map((BatchNumber) null)).isNull();
    }

    @Test
    void shouldMapNullToNullFromLong() {
        assertThat(batchNumberMapper.map((Long) null)).isNull();
    }

    @Test
    void shouldMapToLong() {
        assertThat(batchNumberMapper.map(new BatchNumber(1L))).isEqualTo(1L);
    }

    @Test
    void shouldMapFromLong() {
        assertThat(batchNumberMapper.map(1L)).isEqualTo(new BatchNumber(1L));
    }

}