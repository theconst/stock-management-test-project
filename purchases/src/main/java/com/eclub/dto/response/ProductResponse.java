package com.eclub.dto.response;

import com.eclub.dto.doc.ProductDescription;
import com.eclub.dto.doc.ProductId;
import com.eclub.dto.doc.ProductName;
import com.eclub.dto.doc.ProductVendor;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
public record ProductResponse(
        @ProductId Long id,
        @ProductName String name,
        @ProductVendor String vendor,
        @ProductDescription String description) {
}
