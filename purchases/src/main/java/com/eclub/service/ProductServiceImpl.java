package com.eclub.service;

import com.eclub.domain.Product;
import com.eclub.domain.Product.ProductId;
import com.eclub.mapper.ProductEntityToProductMapper;
import com.eclub.mapper.ProductIdMapper;
import com.eclub.mapper.ProductToProductEntityMapper;
import com.eclub.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
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

    @Override
    public Mono<Page<Product>> listProducts(PageRequest pageRequest) {
        return productRepository
                .findAllByOrderByProductId(pageRequest)
                .map(productEntityToProductMapper::map)
                .collectList()
                .map(page -> new PageImpl<>(page, pageRequest, page.size()));
    }
}
