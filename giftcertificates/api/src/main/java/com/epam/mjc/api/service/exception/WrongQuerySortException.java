package com.epam.mjc.api.service.exception;

public class WrongQuerySortException extends RuntimeException {
    public WrongQuerySortException(String s) {
        super(s);
    }
}
