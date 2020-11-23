package com.epam.mjc.api.service.exception;

public class GiftCertificateAlreadyExists extends ServiceException {
    public GiftCertificateAlreadyExists(String s) {
        super(s);
    }

    public GiftCertificateAlreadyExists(String s, Object... args) {
        super(s, args);
    }
}
