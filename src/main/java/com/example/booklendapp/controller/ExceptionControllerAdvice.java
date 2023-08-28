package com.example.booklendapp.controller;

import com.example.booklendapp.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionControllerAdvice {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionControllerAdvice.class);

       private static final Map<Class<? extends Exception>, HttpStatus> EXCEPTION_STATUS_MAP = new HashMap<>();
    static {
        EXCEPTION_STATUS_MAP.put(BookIsNotAvailableException.class, HttpStatus.BAD_REQUEST);
        EXCEPTION_STATUS_MAP.put(InvalidBookDataException.class, HttpStatus.BAD_REQUEST);
        EXCEPTION_STATUS_MAP.put(InvalidUserDataException.class, HttpStatus.BAD_REQUEST);
        EXCEPTION_STATUS_MAP.put(LoanIsNotAvailable.class, HttpStatus.BAD_REQUEST);
        EXCEPTION_STATUS_MAP.put(ResourceNotFoundException.class,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        HttpStatus status = EXCEPTION_STATUS_MAP.getOrDefault(ex.getClass(), HttpStatus.INTERNAL_SERVER_ERROR);
        logger.error(ex.getMessage());
        return ResponseEntity.status(status).body(ex.getMessage());
    }
}
