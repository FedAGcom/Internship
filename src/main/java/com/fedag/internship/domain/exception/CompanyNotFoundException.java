package com.fedag.internship.domain.exception;

public class CompanyNotFoundException extends RuntimeException{
    public CompanyNotFoundException(Long id) {
        super("Company with id " + id + " don't exist");
    }
}
