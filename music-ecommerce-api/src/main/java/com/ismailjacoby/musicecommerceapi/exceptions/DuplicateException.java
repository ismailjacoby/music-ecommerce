package com.ismailjacoby.musicecommerceapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DuplicateException extends RuntimeException {
    public DuplicateException(String message) {
        super(message);
    }
}
