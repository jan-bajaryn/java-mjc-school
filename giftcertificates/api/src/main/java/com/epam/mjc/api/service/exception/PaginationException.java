package com.epam.mjc.api.service.exception;

public class PaginationException extends ServiceException {
    public PaginationException(String s) {
        super(s);
    }

    public PaginationException(String s, Object... args) {
        super(s, args);
    }
}
