package com.eclub;

import com.eclub.mapper.MappingConfiguration;
import com.eclub.repository.RepositoryConfiguration;
import com.eclub.service.ProductService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackageClasses = {
    RepositoryConfiguration.class,
    MappingConfiguration.class,
    ProductService.class
})
public class TestApp {
}
