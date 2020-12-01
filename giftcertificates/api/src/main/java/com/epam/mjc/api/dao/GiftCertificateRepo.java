package com.epam.mjc.api.dao;

import com.epam.mjc.api.domain.GiftCertificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GiftCertificateRepo extends JpaRepository<GiftCertificate, Long>, JpaSpecificationExecutor<GiftCertificate> {
    Optional<GiftCertificate> findByName(String name);
}
