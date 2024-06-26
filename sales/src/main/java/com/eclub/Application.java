package com.eclub;


import com.eclub.controller.CustomerController;
import com.eclub.mapper.MappingConfiguration;
import com.eclub.queue.StockQueueConfiguration;
import com.eclub.repository.RepositoryConfiguration;
import com.eclub.service.CustomerService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.reactive.config.EnableWebFlux;

@EnableWebFlux
@EnableScheduling
@SpringBootApplication
@ComponentScan(basePackageClasses = {
        RepositoryConfiguration.class,
        CustomerController.class,
        MappingConfiguration.class,
        CustomerService.class,
        StockQueueConfiguration.class
})
public class Application {
    public static void main(String... args) {
        SpringApplication.run(Application.class);
    }
}