package com.epam.mjc.core.dao.audit;

import com.epam.mjc.api.domain.GiftCertificate;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

public class AuditGiftCertificate {
    @PrePersist
    public void createCertificate(GiftCertificate certificate) {
        LocalDateTime now = LocalDateTime.now();
        certificate.setCreateDate(now);
        certificate.setLastUpdateDate(now);
    }

    @PreUpdate
    public void updateCertificate(GiftCertificate certificate) {
        certificate.setLastUpdateDate(LocalDateTime.now());
    }

}
