package com.epam.mjc.api.service.exception;

public class OrderValidatorException extends ServiceException {
    public OrderValidatorException(String s) {
        super(s);
    }

    public OrderValidatorException(String s, Object... args) {
        super(s, args);
    }
}
