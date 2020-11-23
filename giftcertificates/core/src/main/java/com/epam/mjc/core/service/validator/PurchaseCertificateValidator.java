package com.epam.mjc.core.service.validator;

import com.epam.mjc.api.domain.PurchaseCertificate;
import com.epam.mjc.api.service.exception.PurchaseCertificateValidatorException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PurchaseCertificateValidator {

    private static final int MAX_LENGTH = 255;
    private static final int MIN_VALUE = 1;

    public void validatePurchaseCertificate(PurchaseCertificate pc) {
        if (pc == null) {
            throw new PurchaseCertificateValidatorException("purchase.null");
        }
        validateOldName(pc.getOldName());
        validateCount(pc.getCount());
        validatePriceForOne(pc.getPriceForOne());
    }

    private void validatePriceForOne(BigDecimal priceForOne) {
        if (priceForOne.compareTo(BigDecimal.ZERO) <= 0) {
            throw new PurchaseCertificateValidatorException("purchase.wrong-price-for-one", priceForOne);
        }
    }

    private void validateCount(Integer count) {
        if (count == null || count < MIN_VALUE) {
            throw new PurchaseCertificateValidatorException("purchase.wrong-count", count);
        }
    }

    private void validateOldName(String oldName) {
        if (oldName == null || oldName.isEmpty() || oldName.length() > MAX_LENGTH) {
            throw new PurchaseCertificateValidatorException("purchase.wrong-old-name", oldName);
        }
    }
}
