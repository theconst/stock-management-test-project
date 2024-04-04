package com.eclub.service;

import com.eclub.domain.Price;
import com.eclub.domain.Product;
import com.eclub.domain.Product.ProductId;
import com.eclub.domain.StockItem.StockItemId;
import com.eclub.mapper.ProductEntityToProductMapper;
import com.eclub.mapper.ProductIdMapper;
import com.eclub.mapper.ProductToProductEntityMapper;
import com.eclub.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
class ProductServiceImpl implements ProductService {
    private final ProductIdMapper productIdMapper;
    private final ProductEntityToProductMapper productEntityToProductMapper;
    private final ProductToProductEntityMapper productToProductEntityMapper;
    private final ProductRepository productRepository;

    @Override
    public Mono<Product> getProduct(ProductId id) {
        return productRepository
                .findById(productIdMapper.map(id))
                .map(productEntityToProductMapper::map);
    }

    @Override
    public Mono<Product> upsertProduct(Product product) {
        return productRepository
                .save(productToProductEntityMapper.map(product))
                .map(productEntityToProductMapper::map);
    }

    //TODO: design
    @Override
    public Mono<StockItemId> buyProduct(ProductId productId, Price sellingPrice, int quantity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Flux<Product> listStock() {
        return productRepository
                .findAll()
                .map(productEntityToProductMapper::map);
    }
}
