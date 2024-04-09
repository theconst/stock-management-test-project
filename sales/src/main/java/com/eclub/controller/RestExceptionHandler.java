package com.eclub.controller;

import com.eclub.domain.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

import java.util.Map;

import static java.util.stream.Collectors.toMap;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    Mono<ResponseEntity<Map<String, String>>> handleNotFound(NotFoundException notFoundException) {
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", notFoundException.getMessage())));
    }

    // Error from not blank is not propagated
    // https://stackoverflow.com/questions/64892157/spring-boot-valid-doesnt-display-message-from-notblank
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return ex.getBindingResult().getAllErrors().stream().map(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            return Map.entry(fieldName, errorMessage);
        }).collect(toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
