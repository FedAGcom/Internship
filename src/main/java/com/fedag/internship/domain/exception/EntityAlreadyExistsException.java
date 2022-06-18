package com.fedag.internship.domain.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(BAD_REQUEST)
public class EntityAlreadyExistsException extends RuntimeException {
    public EntityAlreadyExistsException(String objectType, String criteria, Object value) {
        super(String.format("%s with %s: %s already exists", objectType, criteria, value));
    }
}
