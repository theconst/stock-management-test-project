package com.eclub;


import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;


/**
 * Web-layer integration test.
 * Only messaging functionality is mocked. See {@link ControllerApp}
 */

@Retention(RUNTIME)
@ActiveProfiles("test")
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
@SpringBootTest(classes = {ServiceApp.class, ControllerApp.class}, webEnvironment = RANDOM_PORT)
public @interface ControllerTest {
}
