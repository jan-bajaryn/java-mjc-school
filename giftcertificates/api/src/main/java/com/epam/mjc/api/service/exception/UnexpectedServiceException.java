package com.epam.mjc.api.service.exception;

public class UnexpectedServiceException extends RuntimeException {
    public UnexpectedServiceException(String s) {
        super(s);
    }
}
