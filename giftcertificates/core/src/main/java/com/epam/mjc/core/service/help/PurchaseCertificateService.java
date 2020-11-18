package com.epam.mjc.core.service.help;

import com.epam.mjc.api.domain.PurchaseCertificate;
import com.epam.mjc.core.dao.PurchaseCertificateDao;
import com.epam.mjc.core.service.validator.PurchaseCertificateValidator;
import org.springframework.stereotype.Service;

@Service
public class PurchaseCertificateService {

    private final PurchaseCertificateDao purchaseCertificateDao;
    private final PurchaseCertificateValidator validator;

    public PurchaseCertificateService(PurchaseCertificateDao purchaseCertificateDao, PurchaseCertificateValidator validator) {
        this.purchaseCertificateDao = purchaseCertificateDao;
        this.validator = validator;
    }

    public PurchaseCertificate create(PurchaseCertificate pc) {
        validator.validatePurchaseCertificate(pc);
        return purchaseCertificateDao.create(pc);
    }
}
