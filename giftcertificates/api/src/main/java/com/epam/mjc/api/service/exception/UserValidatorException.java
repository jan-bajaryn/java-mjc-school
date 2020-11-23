package com.epam.mjc.api.service.exception;

public class UserValidatorException extends ServiceException {
    public UserValidatorException(String s) {
        super(s);
    }

    public UserValidatorException(String s, Object... args) {
        super(s, args);
    }
}
