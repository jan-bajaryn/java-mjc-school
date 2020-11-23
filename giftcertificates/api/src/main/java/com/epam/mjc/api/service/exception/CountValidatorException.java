package com.epam.mjc.api.service.exception;

public class CountValidatorException extends ServiceException {
    public CountValidatorException(String s) {
        super(s);
    }

    public CountValidatorException(String s, Object... args) {
        super(s, args);
    }
}
