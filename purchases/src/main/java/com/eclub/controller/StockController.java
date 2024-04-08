package com.eclub.controller;


import com.eclub.dto.response.StockItemResponse;
import com.eclub.dto.response.StockOperationStatusResponse;
import com.eclub.mapper.OperationIdMapper;
import com.eclub.mapper.StockItemIdMapper;
import com.eclub.mapper.StockItemToStockItemResponseMapper;
import com.eclub.service.StockService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("stock-items")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class StockController {
    private final OperationIdMapper operationIdMapper;
    private final StockItemIdMapper stockItemIdMapper;
    private final StockItemToStockItemResponseMapper stockItemToStockItemResponseMapper;
    private final StockService stockService;

    @GetMapping("/{id}")
    public Mono<StockItemResponse> getStockItemById(@PathVariable Long id) {
        return stockService
                .getStockItem(stockItemIdMapper.map(id))
                .map(stockItemToStockItemResponseMapper::map);
    }

    @GetMapping("operations/{id}/status")
    public Mono<StockOperationStatusResponse> getOperationStatus(@PathVariable("id") String id) {
        return stockService.isOperationProcessed(operationIdMapper.map(id))
                .map(exists -> exists
                        ? StockOperationStatusResponse.processed(id)
                        : StockOperationStatusResponse.pending(id));
    }

    @GetMapping("/")
    public Mono<Page<StockItemResponse>> listAllStockItems(
            @RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "20") int pageSize) {
        return stockService
                .listStock(PageRequest.of(pageNumber, pageSize))
                .map(p -> p.map(stockItemToStockItemResponseMapper::map));
    }
}
