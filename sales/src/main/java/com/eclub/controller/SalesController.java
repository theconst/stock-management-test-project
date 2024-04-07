package com.eclub.controller;

import com.eclub.domain.SaleItem;
import com.eclub.dto.SaleDto;
import com.eclub.dto.SaleResponseDto;
import com.eclub.mapper.SaleDtoToSellItemMapper;
import com.eclub.mapper.SaleItemToSaleDtoMapper;
import com.eclub.mapper.SaleRecordToSaleResponseDtoMapper;
import com.eclub.service.SaleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/sales")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SalesController {
    private final SaleService saleService;

    private final SaleDtoToSellItemMapper saleDtoToSellItemMapper;
    private final SaleRecordToSaleResponseDtoMapper saleRecordToSaleResponseDtoMapper;
    private final SaleItemToSaleDtoMapper saleItemToSaleDtoMapper;

    @GetMapping("/{id}")
    public Mono<SaleDto> findSaleItemById(@PathVariable("id") Long id) {
        return saleService.findSaleById(new SaleItem.SaleItemId(id))
                .map(saleItemToSaleDtoMapper::map);
    }

    @GetMapping("/")
    public Mono<Page<SaleDto>> listSales(
            @RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "20") int pageSize) {
        return saleService
                .listSales(PageRequest.of(pageNumber, pageSize))
                .map(page -> page.map(saleItemToSaleDtoMapper::map));
    }

    @PutMapping("/")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<SaleResponseDto> sell(@RequestBody SaleDto sale) {
        return saleService.recordSale(saleDtoToSellItemMapper.map(sale))
                .map(saleRecordToSaleResponseDtoMapper::map);
    }
}
