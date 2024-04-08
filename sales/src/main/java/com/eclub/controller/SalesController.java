package com.eclub.controller;

import com.eclub.domain.SaleItem.SaleItemId;
import com.eclub.dto.request.SaleRequest;
import com.eclub.dto.response.SaleCreatedResponse;
import com.eclub.dto.response.SaleResponse;
import com.eclub.mapper.SaleItemAndStockOperationIdToSaleCreatedResponseMapper;
import com.eclub.mapper.SaleItemToSaleResponseMapper;
import com.eclub.mapper.SaleRequestToSellItemMapper;
import com.eclub.service.SaleService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/sales")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SalesController {
    private final SaleService saleService;

    private final SaleRequestToSellItemMapper saleRequestToSellItemMapper;
    private final SaleItemAndStockOperationIdToSaleCreatedResponseMapper
            saleItemAndStockOperationIdToSaleCreatedResponseMapper;
    private final SaleItemToSaleResponseMapper saleItemToSaleResponseMapper;

    @GetMapping("/{id}")
    @Operation(summary = "Find sale item by id")
    public Mono<SaleResponse> findSaleItemById(@PathVariable("id") Long id) {
        return saleService.findSaleById(new SaleItemId(id))
                .map(saleItemToSaleResponseMapper::map);
    }

    @GetMapping("/")
    @Operation(summary = "List sale items. Pagination is done by id")
    public Mono<Page<SaleResponse>> listSales(
            @RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "20") int pageSize) {
        return saleService.listSales(PageRequest.of(pageNumber, pageSize))
                .map(page -> page.map(saleItemToSaleResponseMapper::map));
    }

    @PutMapping("/")
    @Operation(summary = "Sell products. Returns sale record with pending stock operation. " +
            "Check status in <purchase-service-ulr>/stock-items/operations/{id}/status")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<SaleCreatedResponse> sell(@Valid @RequestBody SaleRequest sale) {
        return saleService.recordSale(saleRequestToSellItemMapper.map(sale))
                .map(saleItemAndStockOperationIdToSaleCreatedResponseMapper::map);
    }
}
