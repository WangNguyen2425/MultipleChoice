package com.demo.app.controller;

import com.demo.app.dto.message.ErrorResponse;
import com.demo.app.exception.EntityNotFoundException;
import com.demo.app.exception.FieldExistedException;
import com.demo.app.exception.FileInputException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundExceptions(Exception ex){
        EntityNotFoundException customEx = (EntityNotFoundException) ex;
        HttpStatus status = customEx.getStatus();
        return new ResponseEntity<>(new ErrorResponse(status, customEx.getMessage()), status);
    }

    @ExceptionHandler(FieldExistedException.class)
    public ResponseEntity<ErrorResponse> handleFieldExistedExceptions(Exception ex){
        FieldExistedException customEx = (FieldExistedException) ex;
        HttpStatus status = customEx.getStatus();
        return new ResponseEntity<>(new ErrorResponse(status, customEx.getMessage()), status);
    }

    @ExceptionHandler(FileInputException.class)
    public ResponseEntity<ErrorResponse> handleIOException(Exception ex){
        FileInputException customEx = (FileInputException) ex;
        HttpStatus status = customEx.getStatus();
        return new ResponseEntity<>(new ErrorResponse(status, ex.getMessage()), status);
    }



}
