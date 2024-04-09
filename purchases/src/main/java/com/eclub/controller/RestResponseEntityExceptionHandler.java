package com.eclub.controller;

import com.eclub.domain.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    Mono<ResponseEntity<Map<String, String>>> handleNotFound(NotFoundException notFoundException) {
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", notFoundException.getMessage())));
    }

}
