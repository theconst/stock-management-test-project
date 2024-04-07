package com.eclub.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.ZonedDateTime;

@AllArgsConstructor
@Data
@Table("stock_operation")
public final class StockOperationEntity {
    @Column("operation_id")
    String operationId;
    ZonedDateTime processed;
}
