package com.epam.mjc.api.service.exception;

public class MissedIdAndNameException extends ServiceException {
    public MissedIdAndNameException(String s, Object... args) {
        super(s, args);
    }

    public MissedIdAndNameException(String s) {
        super(s);
    }
}
