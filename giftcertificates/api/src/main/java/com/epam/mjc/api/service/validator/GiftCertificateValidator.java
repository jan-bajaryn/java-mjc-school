package com.epam.mjc.api.service.validator;

import com.epam.mjc.api.domain.GiftCertificate;

public interface GiftCertificateValidator {
    void validateGiftCertificate(GiftCertificate giftCertificate);
    void validateGiftCertificateId(Long id);

    void validateGiftCertificateName(String name);
}
