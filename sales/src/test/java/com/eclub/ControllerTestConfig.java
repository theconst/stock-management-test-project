package com.eclub;

import com.eclub.controller.CustomerController;
import com.eclub.queue.RemoveFromStockPublisher;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;

/**
 * Configuration for web layer tests.
 * All messaging functionality is mocked.
 */
@TestConfiguration
@ComponentScan(basePackageClasses = CustomerController.class)
public class ControllerTestConfig {

    @MockBean
    RemoveFromStockPublisher addToStockPublisher;
}
