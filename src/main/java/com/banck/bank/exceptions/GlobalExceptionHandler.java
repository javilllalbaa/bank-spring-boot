package com.banck.bank.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AppException.class)
    protected ResponseEntity<Object> handleUserNotFoundException(AppException ex) {
        return new ResponseEntity<>(ex.getMessage(), ex.getStatus());
    }
}
