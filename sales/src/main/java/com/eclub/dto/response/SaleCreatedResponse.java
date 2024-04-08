package com.eclub.dto.response;

import com.eclub.dto.request.SaleRequest;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
public record SaleCreatedResponse(String stockOperationId, SaleRequest sale) {
}
