package com.eclub.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.ZonedDateTime;

@Data
@Table("stock_operation")
public final class StockOperationEntity implements Persistable<String> {
    @Id
    @Column("operation_id")
    String operationId;
    ZonedDateTime processed;
    @Transient
    boolean isNew;

    @Override
    public String getId() {
        return operationId;
    }

    public static StockOperationEntity of(String operationId, ZonedDateTime processed) {
        var stockOperation = new StockOperationEntity();

        stockOperation.setOperationId(operationId);
        stockOperation.setProcessed(processed);
        stockOperation.setNew(true);

        return stockOperation;
    }
}
