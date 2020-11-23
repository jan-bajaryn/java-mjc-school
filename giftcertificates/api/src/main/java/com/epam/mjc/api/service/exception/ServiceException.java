package com.epam.mjc.api.service.exception;

public abstract class ServiceException extends RuntimeException {

    private final Object[] args;

    public ServiceException(String s, Object... args) {
        super(s);
        this.args = args;
    }

    public Object[] getArgs() {
        return args;
    }
}
