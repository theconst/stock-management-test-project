package com.eclub.mapper;

import com.eclub.domain.Purchase;
import com.eclub.domain.Sale;
import com.eclub.domain.StockOperation;
import com.eclub.queue.message.PurchaseMessage;
import com.eclub.queue.message.SellMessage;
import com.eclub.queue.message.StockTransactionMessage;
import org.mapstruct.Mapper;

@Mapper(config = MappingConfiguration.class)
public interface StockTransactionMessageToStockOperationMapper {

    default StockOperation map(StockTransactionMessage transaction) {
        // JAVA21: migrate to switch pattern matching
        if (transaction instanceof PurchaseMessage pm) {
            return map(pm);
        } else if (transaction instanceof SellMessage sm) {
            return map(sm);
        }
        throw new IllegalArgumentException("Transaction type %s not supported".formatted(transaction));
    }

    Purchase map(PurchaseMessage pm);

    Sale map(SellMessage sm);
}
