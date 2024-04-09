package com.eclub.controller;

import com.eclub.domain.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

import java.util.Map;

import static java.util.stream.Collectors.toMap;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    Mono<ResponseEntity<Map<String, String>>> handleNotFound(NotFoundException notFoundException) {
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", notFoundException.getMessage())));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(WebExchangeBindException.class)
    public Map<String, String> handleValidationExceptions(WebExchangeBindException ex) {
        return ex.getBindingResult().getAllErrors().stream()
                .map(FieldError.class::cast)
                .collect(toMap(RestExceptionHandler::withViolationSuffix, FieldError::getDefaultMessage));
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DataIntegrityViolationException.class)
    Map<String, String> handleViolationException(DataIntegrityViolationException ex) {
        log.error("Data integrity violation:", ex);
        return Map.of("error", "Operation not possible because it violates data integrity constraints");
    }

    private static String withViolationSuffix(FieldError fieldError) {
        return fieldError.getField() + "Violation";
    }
}
