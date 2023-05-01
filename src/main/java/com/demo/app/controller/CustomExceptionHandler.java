package com.demo.app.controller;

import com.demo.app.dto.message.ErrorResponse;
import com.demo.app.exception.EntityNotFoundException;
import com.demo.app.exception.FieldExistedException;
import com.demo.app.exception.FileInputException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleMethodArgumentNotValid(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return errors;
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleEntityNotFoundExceptions(Exception ex){
        EntityNotFoundException customEx = (EntityNotFoundException) ex;
        HttpStatus status = customEx.getStatus();
        return new ErrorResponse(status, customEx.getMessage());
    }

    @ExceptionHandler(FieldExistedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleFieldExistedExceptions(Exception ex){
        FieldExistedException customEx = (FieldExistedException) ex;
        HttpStatus status = customEx.getStatus();
        return new ErrorResponse(status, customEx.getMessage());
    }

    @ExceptionHandler(FileInputException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public ErrorResponse handleIOException(Exception ex){
        FileInputException customEx = (FileInputException) ex;
        HttpStatus status = customEx.getStatus();
        return new ErrorResponse(status, ex.getMessage());
    }



}
