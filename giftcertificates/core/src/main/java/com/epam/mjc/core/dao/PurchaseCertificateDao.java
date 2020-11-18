package com.epam.mjc.core.dao;

import com.epam.mjc.api.domain.PurchaseCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
public class PurchaseCertificateDao {

    @PersistenceContext
    private final EntityManager entityManager;

    @Autowired
    public PurchaseCertificateDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public PurchaseCertificate create(PurchaseCertificate pc) {
        entityManager.persist(pc);
        return pc;
    }
}
