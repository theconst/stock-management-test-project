package com.eclub;


import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;


/**
 * Web-layer integration test.
 * Only messaging functionality is mocked. See {@link ControllerApp}
 */

@Retention(RUNTIME)
@ActiveProfiles("test")
@SpringBootTest(classes = {ServiceApp.class, ControllerApp.class}, webEnvironment = RANDOM_PORT)
public @interface ControllerTest {
}
