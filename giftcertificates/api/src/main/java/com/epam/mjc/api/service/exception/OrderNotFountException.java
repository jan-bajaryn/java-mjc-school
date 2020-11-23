package com.epam.mjc.api.service.exception;

public class OrderNotFountException extends ServiceException {
    public OrderNotFountException(String s) {
        super(s);
    }

    public OrderNotFountException(String s, Object... args) {
        super(s, args);
    }
}
