package com.epam.mjc.api.service.exception;

public class GiftCertificateNotFoundException extends ServiceException {
    public GiftCertificateNotFoundException(String s, Object... args) {
        super(s, args);
    }

    public GiftCertificateNotFoundException(String s) {
        super(s);
    }
}
