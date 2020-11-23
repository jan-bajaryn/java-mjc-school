package com.epam.mjc.api.service.exception;

public class TagAlreadyExistsException extends ServiceException {
    public TagAlreadyExistsException(String s) {
        super(s);
    }

    public TagAlreadyExistsException(String s, Object... args) {
        super(s, args);
    }
}
