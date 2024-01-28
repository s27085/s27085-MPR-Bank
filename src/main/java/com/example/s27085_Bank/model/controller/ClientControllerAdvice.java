package com.example.s27085_Bank.model.controller;

import com.example.s27085_Bank.exceptions.ClientNotFoundException;
import com.example.s27085_Bank.exceptions.ValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ClientControllerAdvice {
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidationexception(ValidationException e){
        ErrorResponse errorResponse = new ErrorResponse(e.getMessageWithField());
        if(!e.getErrors().isEmpty()){
            errorResponse = new ErrorResponse("Validation error", e.getErrors());
        }
        return ResponseEntity.badRequest().body(errorResponse);
    }
    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCarNotFoundException(ClientNotFoundException e){
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }
}
