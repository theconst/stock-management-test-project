package com.eclub;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;


@SpringBootApplication
@Import(ServiceTestConfig.class)
public class TestApp {
}
