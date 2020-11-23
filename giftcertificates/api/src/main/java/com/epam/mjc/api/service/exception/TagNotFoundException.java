package com.epam.mjc.api.service.exception;

public class TagNotFoundException extends ServiceException {
    public TagNotFoundException(String s) {
        super(s);
    }

    public TagNotFoundException(String s, Object... args) {
        super(s, args);
    }
}
