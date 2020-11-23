package com.epam.mjc.api.service.exception;

public class EmptyGiftCertificates extends ServiceException {
    public EmptyGiftCertificates(String s) {
        super(s);
    }

    public EmptyGiftCertificates(String s, Object... args) {
        super(s, args);
    }
}
