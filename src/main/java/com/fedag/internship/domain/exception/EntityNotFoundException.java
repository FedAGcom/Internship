package com.fedag.internship.domain.exception;

public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(String objectType, String criteria, Object value) {
        super(String.format("%s with %s: %s not found", objectType, criteria, value));
    }
}
