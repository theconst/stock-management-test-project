package com.eclub.service;

import com.eclub.common.DbTemplate;
import com.eclub.ServiceTest;
import com.eclub.domain.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;

import static com.eclub.service.Products.IDEA_PAD;
import static com.eclub.service.Products.MACBOOK;
import static com.eclub.service.Products.PRODUCT_ID_1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

@ServiceTest
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
class ProductServiceTest {
    @Autowired DbTemplate db;
    @Autowired ProductService productService;

    @Test
    void shouldGetProductById() {
        db.insertProduct(IDEA_PAD);

        var product = productService.getProduct(PRODUCT_ID_1).block();

        assertThat(product).isEqualTo(IDEA_PAD);
    }

    @Test
    void shouldCreateProduct() {
        var product = productService.upsertProduct(IDEA_PAD.toBuilder().id(null).build()).block();

        assertThat(product).isEqualTo(IDEA_PAD.toBuilder().id(new Product.ProductId(1)).build());

        var stored = db.selectProductByName(IDEA_PAD.name());
        assertThat(stored).isNotEmpty();
    }

    @Test
    void shouldUpdateProduct() {
        db.insertProduct(IDEA_PAD);
        Product updated = IDEA_PAD.toBuilder().vendor("Shingway ltd.").build();

        var product = productService.upsertProduct(updated).block();

        assertThat(product).isEqualTo(updated);

        assertThat(db.selectProductByVendor(IDEA_PAD.vendor())).isNull();
        assertThat(db.selectProductByVendor("Shingway ltd.")).isNotEmpty();
    }

    @Test
    void shouldListProducts() {
        db.insertProduct(IDEA_PAD);
        db.insertProduct(MACBOOK);

        var page1 = productService.listProducts(PageRequest.of(0, 1)).block();
        var page2 = productService.listProducts(PageRequest.of(1, 1)).block();

        var largerPage = productService.listProducts(PageRequest.of(0, 100)).block();

        assertThat(page1).hasSize(1);
        assertThat(page2.getTotalElements()).isEqualTo(2);
        assertThat(page1).containsExactly(IDEA_PAD);

        assertThat(page2).hasSize(1);
        assertThat(page2.getTotalElements()).isEqualTo(2);
        assertThat(page2).containsExactly(MACBOOK);

        assertThat(largerPage.getTotalPages()).isEqualTo(1);
        assertThat(largerPage.getTotalElements()).isEqualTo(2);
        assertThat(largerPage).containsExactly(IDEA_PAD, MACBOOK);
    }
}
