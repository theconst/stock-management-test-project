package com.eclub;

import com.eclub.controller.ProductController;
import com.eclub.mapper.MappingConfiguration;
import com.eclub.queue.PurchaseQueueConfiguration;
import com.eclub.repository.RepositoryConfiguration;
import com.eclub.service.ProductService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.reactive.config.EnableWebFlux;

@EnableWebFlux
@SpringBootApplication
@ComponentScan(basePackageClasses = {
        RepositoryConfiguration.class,
        ProductController.class,
        MappingConfiguration.class,
        ProductService.class,
        PurchaseQueueConfiguration.class
})
public class Application {
    public static void main(String... args) {
        SpringApplication.run(Application.class);
    }
}