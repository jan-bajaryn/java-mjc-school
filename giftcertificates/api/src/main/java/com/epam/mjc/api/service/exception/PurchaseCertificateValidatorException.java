package com.epam.mjc.api.service.exception;

public class PurchaseCertificateValidatorException extends ServiceException {
    public PurchaseCertificateValidatorException(String s) {
        super(s);
    }

    public PurchaseCertificateValidatorException(String s, Object... args) {
        super(s, args);
    }
}
