package com.eclub.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("remove_from_stock_outbox")
public class RemoveFromStockEntity implements Persistable<String> {
    @Id
    @Column("operation_id")
    String operationId;

    @Column("stock_item_id")
    long stockItemId;

    int quantity;

    @Transient
    boolean isNew = true;

    @Override
    public String getId() {
        return operationId;
    }
}
