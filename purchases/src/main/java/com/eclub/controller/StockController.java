package com.eclub.controller;


import com.eclub.dto.StockItemDto;
import com.eclub.mapper.StockItemIdMapper;
import com.eclub.mapper.StockItemToStockItemDtoMapper;
import com.eclub.service.StockService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("stock-items")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class StockController {
    private final StockItemIdMapper stockItemIdMapper;
    private final StockItemToStockItemDtoMapper stockItemToStockItemDtoMapper;
    private final StockService stockService;

    @GetMapping("/{id}")
    public Mono<StockItemDto> getStockItemById(@PathVariable Long id) {
        return stockService
                .getStockItem(stockItemIdMapper.map(id))
                .map(stockItemToStockItemDtoMapper::map);
    }

    //TODO(kkovalchuk): pagination
    @GetMapping("/")
    public Flux<StockItemDto> listAllStockItems() {
        return stockService
                .listStock()
                .map(stockItemToStockItemDtoMapper::map);
    }
}
