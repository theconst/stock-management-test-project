package com.eclub.controller;


import com.eclub.dto.PurchaseDto;
import com.eclub.dto.StockItemDto;
import com.eclub.mapper.StockItemIdMapper;
import com.eclub.mapper.StockItemToStockItemDtoMapper;
import com.eclub.queue.StockUpdatePublisher;
import com.eclub.service.StockService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("stock-items")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class StockController {
    private final StockItemIdMapper stockItemIdMapper;
    private final StockItemToStockItemDtoMapper stockItemToStockItemDtoMapper;
    private final PurchaseDtoToPurchaseMessageMapper purchaseDtoToPurchaseMessageMapperMapper;
    private final StockService stockService;
    private final StockUpdatePublisher stockUpdatePublisher;

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

    //TODO(kkovalchuk): move to separate controller?
    @PutMapping("/purchases")
    public Mono<ResponseEntity<Void>> purchase(@RequestBody PurchaseDto purchase) {
        return stockUpdatePublisher
                .publish(purchaseDtoToPurchaseMessageMapperMapper.map(purchase))
                .thenReturn(ResponseEntity.accepted().build());
    }
}
