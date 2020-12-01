package com.epam.mjc.api.dao;

import com.epam.mjc.api.domain.PurchaseCertificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseCertificateRepo extends JpaRepository<PurchaseCertificate, Long> {
}
