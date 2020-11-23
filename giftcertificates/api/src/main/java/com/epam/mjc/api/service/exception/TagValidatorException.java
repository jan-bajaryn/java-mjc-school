package com.epam.mjc.api.service.exception;

public class TagValidatorException extends ServiceException {
    public TagValidatorException(String s) {
        super(s);
    }

    public TagValidatorException(String s, Object... args) {
        super(s, args);
    }
}
