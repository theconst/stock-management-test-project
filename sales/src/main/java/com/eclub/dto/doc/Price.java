package com.eclub.dto.doc;

import io.swagger.v3.oas.annotations.media.Schema;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Schema(description = "Total price of sale")
@Retention(RetentionPolicy.RUNTIME)
public @interface Price {
}
