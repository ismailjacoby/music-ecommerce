package com.ismailjacoby.musicecommerceapi.exceptions;

import com.ismailjacoby.musicecommerceapi.models.dtos.ErrorResponseDTO;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // BadCredentialsException  (incorrect username or password)
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponseDTO handleBadCredentialsException(BadCredentialsException e) {
        return new ErrorResponseDTO("Authentication failed", e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    // DuplicateException
    @ExceptionHandler(DuplicateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDTO handleDuplicateException(DuplicateException e) {
        return new ErrorResponseDTO("Duplicate Found", e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // IllegalArgumentException
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDTO handleIllegalArgumentException(IllegalArgumentException e) {
        return new ErrorResponseDTO("Invalid Argument", e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // InvalidTokenException
    @ExceptionHandler(InvalidTokenException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDTO handleInvalidTokenException(InvalidTokenException e) {
        return new ErrorResponseDTO("Invalid Token", e.getMessage(), HttpStatus.BAD_REQUEST);
    }


    // MethodArgumentNotValidException (Validation errors)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDTO handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<String> errorMessages = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getDefaultMessage())
                .collect(Collectors.toList());

        String message = String.join(", ", errorMessages);

        return new ErrorResponseDTO("Validation failed", message, HttpStatus.BAD_REQUEST);
    }

    // NotFoundException
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseDTO handleNotFoundException(NotFoundException e) {
        return new ErrorResponseDTO("Not Found", e.getMessage(), HttpStatus.NOT_FOUND);
    }

}
