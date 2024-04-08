package com.eclub.mapper;

import com.eclub.domain.StockOperation.OperationId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class OperationIdMapperTest {
    @Spy
    OperationIdMapper batchNumberMapper;

    @Test
    void shouldMapNullToNullFromStockId() {
        assertThat(batchNumberMapper.map((OperationId) null)).isNull();
    }

    @Test
    void shouldMapNullToNullFromLong() {
        assertThat(batchNumberMapper.map((String) null)).isNull();
    }

    @Test
    void shouldMapToLong() {
        assertThat(batchNumberMapper.map(new OperationId("1"))).isEqualTo("1");
    }

    @Test
    void shouldMapFromLong() {
        assertThat(batchNumberMapper.map("1")).isEqualTo(new OperationId("1"));
    }
}