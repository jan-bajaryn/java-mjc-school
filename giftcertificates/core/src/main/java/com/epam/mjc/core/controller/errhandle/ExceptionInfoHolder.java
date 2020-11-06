package com.epam.mjc.core.controller.errhandle;

public class ExceptionInfoHolder {
    private String message;
    private String errorCode;

    public ExceptionInfoHolder() {
    }

    public ExceptionInfoHolder(String message, String errorCode) {
        this.message = message;
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
