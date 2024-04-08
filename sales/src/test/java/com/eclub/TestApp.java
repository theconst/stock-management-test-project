package com.eclub;

import com.eclub.mapper.MappingConfiguration;
import com.eclub.repository.RepositoryConfiguration;
import com.eclub.service.SaleService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackageClasses = {
    RepositoryConfiguration.class,
    MappingConfiguration.class,
    SaleService.class
})
public class TestApp {
}