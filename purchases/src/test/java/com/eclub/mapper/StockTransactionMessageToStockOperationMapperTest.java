package com.eclub.mapper;

import com.eclub.message.StockOperationMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class StockTransactionMessageToStockOperationMapperTest {

    @Spy
    StockTransactionMessageToStockOperationMapper sut;

    @Test
    void shouldMapNullToNull() {
        assertThat(sut.map((StockOperationMessage) null)).isNull();
    }

    @Test
    @SuppressWarnings("unchecked")
    void shouldMapAllSubclassesWithoutError() {
        // JAVA 21: verified with switch on compile time
        for (var message : (Class<StockOperationMessage>[]) StockOperationMessage.class.getPermittedSubclasses()) {
            sut.map(mock(message));
        }
    }
}