package com.eclub.service;

import com.eclub.domain.Product;
import com.eclub.domain.Product.ProductId;

import java.util.List;

public interface ProductService {

    void createProduct(Product product);

    void buyProduct(ProductId productId, int quantity);

    List<Product> listStock();
}
