package com.eclub;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Service-level integration test
 * Test excludes queue and web components, but includes local database (see application-test.yaml)
 */
@SpringBootTest(classes = ServiceApp.class)
@ActiveProfiles("test")
@Retention(RetentionPolicy.RUNTIME)
public @interface ServiceTest {
}
