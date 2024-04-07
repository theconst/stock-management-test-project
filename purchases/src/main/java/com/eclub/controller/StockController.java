package com.eclub.controller;


import com.eclub.dto.StockItemDto;
import com.eclub.dto.StockOperationStatusDto;
import com.eclub.mapper.OperationIdMapper;
import com.eclub.mapper.StockItemIdMapper;
import com.eclub.mapper.StockItemToStockItemDtoMapper;
import com.eclub.service.StockService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("stock-items")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class StockController {
    private final OperationIdMapper operationIdMapper;
    private final StockItemIdMapper stockItemIdMapper;
    private final StockItemToStockItemDtoMapper stockItemToStockItemDtoMapper;
    private final StockService stockService;

    @GetMapping("/{id}")
    public Mono<StockItemDto> getStockItemById(@PathVariable Long id) {
        return stockService
                .getStockItem(stockItemIdMapper.map(id))
                .map(stockItemToStockItemDtoMapper::map);
    }

    @GetMapping("operations/{id}/status")
    public Mono<StockOperationStatusDto> getOperationStatus(@PathVariable("id") String id) {
        return stockService.isOperationProcessed(operationIdMapper.map(id))
                .map(exists -> exists ? StockOperationStatusDto.processed(id) : StockOperationStatusDto.pending(id));
    }

    @GetMapping("/")
    public Mono<Page<StockItemDto>> listAllStockItems(
            @RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "20") int pageSize) {
        return stockService
                .listStock(PageRequest.of(pageNumber, pageSize))
                .map(p -> p.map(stockItemToStockItemDtoMapper::map));
    }
}
