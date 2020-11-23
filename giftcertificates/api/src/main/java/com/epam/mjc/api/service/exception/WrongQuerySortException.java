package com.epam.mjc.api.service.exception;

public class WrongQuerySortException extends ServiceException {
    public WrongQuerySortException(String s) {
        super(s);
    }

    public WrongQuerySortException(String s, Object... args) {
        super(s, args);
    }
}
