package com.fedag.internship.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * class EntityNotFoundException for exception when Entity wasn't found in DataBase.
 *
 * @author damir.iusupov
 * @since 2022-05-31
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String objectType, String criteria, Object value) {
        super(String.format("%s with %s: %s not found", objectType, criteria, value));
    }
}
