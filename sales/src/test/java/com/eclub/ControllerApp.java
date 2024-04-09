package com.eclub;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({ServiceTestConfig.class, ControllerTestConfig.class})
public class ControllerApp {
}
