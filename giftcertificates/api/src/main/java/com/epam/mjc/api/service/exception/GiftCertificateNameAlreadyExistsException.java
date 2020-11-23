package com.epam.mjc.api.service.exception;

public class GiftCertificateNameAlreadyExistsException extends ServiceException {
    public GiftCertificateNameAlreadyExistsException(String s) {
        super(s);
    }

    public GiftCertificateNameAlreadyExistsException(String s, Object... args) {
        super(s, args);
    }
}
