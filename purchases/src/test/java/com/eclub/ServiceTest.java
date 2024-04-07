package com.eclub;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Test excludes queue and controller components, but includes local h2 db (see application-test.yaml)
 */
@SpringBootTest(classes = TestApp.class)
@ActiveProfiles("test")
@Retention(RetentionPolicy.RUNTIME)
public @interface ServiceTest {
}
