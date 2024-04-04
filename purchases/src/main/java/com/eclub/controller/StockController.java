package com.eclub.controller;


import com.eclub.dto.PurchaseDto;
import com.eclub.dto.StockItemDto;
import com.eclub.mapper.PurchaseDtoToPurchaseMapper;
import com.eclub.mapper.StockItemIdMapper;
import com.eclub.mapper.StockItemToStockItemDtoMapper;
import com.eclub.service.StockService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("stock-items")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class StockController {
    private final StockItemIdMapper stockItemIdMapper;
    private final StockItemToStockItemDtoMapper stockItemToStockItemDtoMapper;
    private final PurchaseDtoToPurchaseMapper purchaseDtoToPurchaseMapper;
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

    @PutMapping("/purchases")
    public Mono<StockItemDto> purchase(@RequestBody PurchaseDto purchase) {
        return stockService
                .purchase(purchaseDtoToPurchaseMapper.map(purchase))
                .map(stockItemToStockItemDtoMapper::map);
    }
}
