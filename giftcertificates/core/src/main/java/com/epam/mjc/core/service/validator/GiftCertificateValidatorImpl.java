package com.epam.mjc.core.service.validator;

import com.epam.mjc.api.domain.GiftCertificate;
import com.epam.mjc.api.service.exception.GiftCertificateValidatorException;
import com.epam.mjc.api.service.validator.GiftCertificateValidator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class GiftCertificateValidatorImpl implements GiftCertificateValidator {

    private static final int NAME_MAX_LENGTH = 255;
    private static final int DESCRIPTION_MAX_LENGTH = 500;
    private static final int PRICE_MIN_VALUE = 0;
    private static final int DURATION_MIN_VALUE = 0;
    private static final int ID_MIN_VALUE = 1;

    @Override
    public void validateGiftCertificate(GiftCertificate giftCertificate) {
        if (giftCertificate == null) {
            throw new GiftCertificateValidatorException("GiftCertificate is null");
        }

        validateGiftCertificateName(giftCertificate.getName());
        validateGiftCertificateDescription(giftCertificate.getDescription());
        validateGiftCertificatePrice(giftCertificate.getPrice());
        validateGiftCertificateDuration(giftCertificate.getDuration());

    }

    @Override
    public void validateGiftCertificateId(Long id) {
        if (id == null || id < ID_MIN_VALUE) {
            throw new GiftCertificateValidatorException("Id is wrong");
        }
    }

    private void validateGiftCertificateDuration(Integer duration) {
        if (duration == null || duration < DURATION_MIN_VALUE) {
            throw new GiftCertificateValidatorException("Duration is wrong");
        }
    }

    private void validateGiftCertificatePrice(BigDecimal price) {
        if (price == null || price.compareTo(BigDecimal.ZERO) < PRICE_MIN_VALUE) {
            throw new GiftCertificateValidatorException("Price is wrong.");
        }
    }

    private void validateGiftCertificateDescription(String description) {
        if (description == null || description.isEmpty() || description.length() > DESCRIPTION_MAX_LENGTH) {
            throw new GiftCertificateValidatorException("Description is wrong");
        }
    }

    private void validateGiftCertificateName(String name) {
        if (name == null || name.isEmpty() || name.length() > NAME_MAX_LENGTH) {
            throw new GiftCertificateValidatorException("Name is wrong");
        }
    }

}
