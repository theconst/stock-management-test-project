package com.eclub;

import com.eclub.mapper.MappingConfiguration;
import com.eclub.repository.RepositoryConfiguration;
import com.eclub.service.ProductService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

@TestConfiguration
@ComponentScan(basePackageClasses = {
        RepositoryConfiguration.class,
        MappingConfiguration.class,
        ProductService.class
})
public class ServiceTestConfig {
}
