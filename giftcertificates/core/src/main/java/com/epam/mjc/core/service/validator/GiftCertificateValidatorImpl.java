package com.epam.mjc.core.service.validator;

import com.epam.mjc.api.domain.GiftCertificate;
import com.epam.mjc.api.service.exception.GiftCertificateValidatorException;
import com.epam.mjc.api.service.validator.GiftCertificateValidator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class GiftCertificateValidatorImpl implements GiftCertificateValidator {

    @Override
    public void validateGiftCertificate(GiftCertificate giftCertificate) {
        if (giftCertificate == null) {
            throw new GiftCertificateValidatorException("GiftCertificate is null");
        }

        validateGiftCertificateId(giftCertificate.getId());
        validateGiftCertificateName(giftCertificate.getName());
        validateGiftCertificateDescription(giftCertificate.getDescription());
        validateGiftCertificatePrice(giftCertificate.getPrice());
        validateGiftCertificateDuration(giftCertificate.getDuration());

    }

    @Override
    public void validateGiftCertificateId(Long id) {
        if (id == null || id < 0) {
            throw new GiftCertificateValidatorException("Id is wrong");
        }
    }

    private void validateGiftCertificateDuration(Integer duration) {
        if (duration == null || duration < 0) {
            throw new GiftCertificateValidatorException("Duration is wrong");
        }
    }

    private void validateGiftCertificatePrice(BigDecimal price) {
        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new GiftCertificateValidatorException("Price is wrong.");
        }
    }

    private void validateGiftCertificateDescription(String description) {
        if (description == null || description.isEmpty() || description.length() > 500) {
            throw new GiftCertificateValidatorException("Description is wrong");
        }
    }

    private void validateGiftCertificateName(String name) {
        if (name == null || name.isEmpty() || name.length() > 255) {
            throw new GiftCertificateValidatorException("Name is wrong");
        }
    }

}
