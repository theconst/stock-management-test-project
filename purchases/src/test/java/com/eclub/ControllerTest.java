package com.eclub;


import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;


/**
 * Web-layer integration test.
 * Only messaging functionality is mocked. See {@link ControllerTestConfig}
 */

@Retention(RUNTIME)
@ActiveProfiles("test")
@SpringBootTest(classes = ControllerApp.class)
@AutoConfigureMockMvc
public @interface ControllerTest {
}
