package com.eclub.common;

import com.eclub.domain.Product;
import com.eclub.domain.Product.ProductId;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ProductStubs {
    public static final ProductId PRODUCT_ID_1 = new ProductId(1L);
    public static final Product IDEA_PAD = Product.builder()
            .id(PRODUCT_ID_1)
            .name("Lenovo IdeaPad")
            .vendor("Lenovo")
            .description("Cheap laptop")
            .build();
    public static final ProductId PRODUCT_ID_2 = new ProductId(2L);
    public static final Product MACBOOK = Product.builder()
            .id(PRODUCT_ID_2)
            .name("Macbook M3")
            .vendor("Apple")
            .description("Expensive laptop")
            .build();
}
