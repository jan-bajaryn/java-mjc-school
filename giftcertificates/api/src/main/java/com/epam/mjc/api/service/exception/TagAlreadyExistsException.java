package com.epam.mjc.api.service.exception;

public class TagAlreadyExistsException extends RuntimeException {
    public TagAlreadyExistsException(String s) {
        super(s);
    }
}
