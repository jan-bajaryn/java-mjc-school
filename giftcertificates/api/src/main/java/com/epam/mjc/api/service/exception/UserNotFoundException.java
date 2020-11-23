package com.epam.mjc.api.service.exception;

public class UserNotFoundException extends ServiceException {
    public UserNotFoundException(String s) {
        super(s);
    }

    public UserNotFoundException(String s, Object... args) {
        super(s, args);
    }
}
