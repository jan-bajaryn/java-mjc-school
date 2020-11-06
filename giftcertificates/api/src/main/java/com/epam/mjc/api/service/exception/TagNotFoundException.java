package com.epam.mjc.api.service.exception;

public class TagNotFoundException extends RuntimeException {
    public TagNotFoundException(String s) {
        super(s);
    }
}
