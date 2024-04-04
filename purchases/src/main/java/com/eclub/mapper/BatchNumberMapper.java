package com.eclub.mapper;

import com.eclub.domain.StockItem.BatchNumber;
import org.mapstruct.Mapper;

@Mapper(config = MappingConfiguration.class)
public interface BatchNumberMapper {
    default Long map(BatchNumber batchNumber) {
        return batchNumber == null ? null : batchNumber.batchNumber();
    }

    default BatchNumber map(Long batchNumber) {
        return batchNumber == null ? null : new BatchNumber(batchNumber);
    }
}
