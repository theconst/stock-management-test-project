package com.eclub;

import com.eclub.controller.ProductController;
import com.eclub.queue.AddToStockPublisher;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;

/**
 * Configuration for web layer tests.
 * All messaging functionality is mocked.
 */
@SpringBootApplication
@ComponentScan(basePackageClasses = ProductController.class)
public class ControllerApp {

    @MockBean
    AddToStockPublisher addToStockPublisher;
}
