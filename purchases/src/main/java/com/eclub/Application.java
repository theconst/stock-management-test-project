package com.eclub;

import com.eclub.repository.RepositoryConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses = RepositoryConfiguration.class)
public class Application {
    public static void main(String... args) {
        SpringApplication.run(Application.class);
    }
}