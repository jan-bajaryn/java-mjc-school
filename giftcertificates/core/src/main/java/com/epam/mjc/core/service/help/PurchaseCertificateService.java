package com.epam.mjc.core.service.help;

import com.epam.mjc.api.dao.PurchaseCertificateRepo;
import com.epam.mjc.api.domain.PurchaseCertificate;
import com.epam.mjc.core.service.validator.PurchaseCertificateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseCertificateService {

    private final PurchaseCertificateRepo purchaseCertificateRepo;
    private final PurchaseCertificateValidator validator;

    @Autowired
    public PurchaseCertificateService(PurchaseCertificateRepo purchaseCertificateRepo, PurchaseCertificateValidator validator) {
        this.purchaseCertificateRepo = purchaseCertificateRepo;
        this.validator = validator;
    }

    public PurchaseCertificate create(PurchaseCertificate pc) {
        validator.validatePurchaseCertificate(pc);
        return purchaseCertificateRepo.save(pc);
    }
}
