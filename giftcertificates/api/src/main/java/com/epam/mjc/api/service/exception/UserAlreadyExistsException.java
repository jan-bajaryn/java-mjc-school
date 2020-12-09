package com.epam.mjc.api.service.exception;

public class UserAlreadyExistsException extends ServiceException {

    public UserAlreadyExistsException(String s, Object... args) {
        super(s, args);
    }

}
