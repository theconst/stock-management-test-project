package com.eclub.mapper;

import com.eclub.domain.Customer.CustomerId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CustomerIdMapperTest {

    @Spy
    CustomerIdMapper customerIdMapper;

    @Test
    void shouldMapNullToNullFromStockId() {
        assertThat(customerIdMapper.map((CustomerId) null)).isNull();
    }

    @Test
    void shouldMapNullToNullFromLong() {
        assertThat(customerIdMapper.map((Long) null)).isNull();
    }

    @Test
    void shouldMapToLong() {
        assertThat(customerIdMapper.map(new CustomerId(1L))).isEqualTo(1L);
    }

    @Test
    void shouldMapFromLong() {
        assertThat(customerIdMapper.map(1L)).isEqualTo(new CustomerId(1L));
    }
}