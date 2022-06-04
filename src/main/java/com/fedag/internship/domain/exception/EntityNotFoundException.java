package com.fedag.internship.domain.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(NOT_FOUND)
public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String objectType, String criteria, Object value) {
        super(String.format("%s with %s: %s not found", objectType, criteria, value));
    }
}
