package com.eclub.dto.request;

import com.eclub.dto.doc.CustomerId;
import com.eclub.dto.doc.Price;
import com.eclub.dto.doc.Quantity;
import com.eclub.dto.doc.StockItemId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

@Builder
@Jacksonized
public record SaleRequest(
        @StockItemId Long stockItemId,
        @CustomerId Long customerId,
        @Price BigDecimal price,
        @Quantity Integer quantity) {
}
