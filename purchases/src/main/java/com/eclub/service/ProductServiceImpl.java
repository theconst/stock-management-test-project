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
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static com.eclub.util.PagingCollector.collectPages;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    private final ProductIdMapper productIdMapper;
    private final ProductEntityToProductMapper productEntityToProductMapper;
    private final ProductToProductEntityMapper productToProductEntityMapper;

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
                .transform(collectPages(pageRequest, productRepository::count))
                .single();
    }
}
