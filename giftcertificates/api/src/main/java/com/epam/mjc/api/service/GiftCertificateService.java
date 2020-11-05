package com.epam.mjc.api.service;

import com.epam.mjc.api.domain.GiftCertificate;

import java.util.List;

public interface GiftCertificateService {
    List<GiftCertificate> findAll();

    GiftCertificate findById(Long id);

    GiftCertificate create(GiftCertificate giftCertificate);

    boolean deleteById(Long id);
}
