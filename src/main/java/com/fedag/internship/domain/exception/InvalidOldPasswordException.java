package com.fedag.internship.domain.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(BAD_REQUEST)
public class InvalidOldPasswordException extends RuntimeException {
    public InvalidOldPasswordException(String message) {super(message);}
}
