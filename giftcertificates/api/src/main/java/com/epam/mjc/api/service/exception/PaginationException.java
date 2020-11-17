package com.epam.mjc.api.service.exception;

public class PaginationException extends RuntimeException {
    public PaginationException(String s) {
        super(s);
    }
}
