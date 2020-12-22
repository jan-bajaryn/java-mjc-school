package com.epam.mjc.core.service.validator;

import com.epam.mjc.api.domain.GiftCertificate;
import com.epam.mjc.api.domain.Tag;
import com.epam.mjc.api.service.exception.GiftCertificateValidatorException;
import com.epam.mjc.api.service.validator.GiftCertificateValidator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
            throw new GiftCertificateValidatorException("certificate.null");
        }

        validateGiftCertificateName(giftCertificate.getName());
        validateGiftCertificateDescription(giftCertificate.getDescription());
        validateGiftCertificatePrice(giftCertificate.getPrice());
        validateGiftCertificateDuration(giftCertificate.getDuration());
        validateGiftCertificateTags(giftCertificate.getTags());

    }

    private void validateGiftCertificateTags(List<Tag> tags) {
        if (tags != null && !tags.isEmpty()) {
            List<String> names = tags.stream()
                    .map(Tag::getName)
                    .collect(Collectors.toList());
            Set<String> collect = names.stream().filter(i -> Collections.frequency(names, i) > 1)
                    .collect(Collectors.toSet());
            if (!collect.isEmpty()) {
                throw new GiftCertificateValidatorException("certificate.duplicated-tags", Arrays.toString(collect.toArray()));
            }
        }
    }

    @Override
    public void validateGiftCertificateId(Long id) {
        if (id == null || id < ID_MIN_VALUE) {
            throw new GiftCertificateValidatorException("certificate.wrong-id", id);
        }
    }

    private void validateGiftCertificateDuration(Integer duration) {
        if (duration == null || duration < DURATION_MIN_VALUE) {
            throw new GiftCertificateValidatorException("certificate.wrong-duration", duration);
        }
    }

    private void validateGiftCertificatePrice(BigDecimal price) {
        if (price == null || price.compareTo(BigDecimal.ZERO) < PRICE_MIN_VALUE) {
            throw new GiftCertificateValidatorException("certificate.wrong-price", price);
        }
    }

    private void validateGiftCertificateDescription(String description) {
        if (description == null || description.isEmpty() || description.length() > DESCRIPTION_MAX_LENGTH) {
            throw new GiftCertificateValidatorException("certificate.wrong-description", description);
        }
    }

    @Override
    public void validateGiftCertificateName(String name) {
        if (name == null || name.isEmpty() || name.length() > NAME_MAX_LENGTH) {
            throw new GiftCertificateValidatorException("certificate.wrong-name", name);
        }
    }

}
