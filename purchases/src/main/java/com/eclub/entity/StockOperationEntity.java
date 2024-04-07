package com.eclub.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Data
@Table("stock_operation")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StockOperationEntity implements Persistable<String> {
    @Id
    @Column("operation_id")
    String operationId;
    LocalDateTime processed;
    @Transient
    boolean isNew;

    @Override
    public String getId() {
        return operationId;
    }

    public static StockOperationEntity of(String operationId, ZonedDateTime processed) {
        var stockOperation = new StockOperationEntity();

        stockOperation.setOperationId(operationId);

        // force utc timestamps
        stockOperation.setProcessed(processed.withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime());
        stockOperation.setNew(true);

        return stockOperation;
    }
}
