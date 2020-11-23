package com.epam.mjc.api.service.exception;

public class GiftCertificateValidatorException extends ServiceException {
    public GiftCertificateValidatorException(String s) {
        super(s);
    }

    public GiftCertificateValidatorException(String s, Object... args) {
        super(s, args);
    }
}
