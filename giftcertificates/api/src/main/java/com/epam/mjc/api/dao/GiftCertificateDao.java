package com.epam.mjc.api.dao;

import com.epam.mjc.api.domain.GiftCertificate;
import com.epam.mjc.api.util.SearchParams;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateDao {

    GiftCertificate create(GiftCertificate giftCertificate);

    GiftCertificate update(GiftCertificate giftCertificate);

    Optional<GiftCertificate> findById(Long id);

    boolean delete(GiftCertificate giftCertificate);

    List<GiftCertificate> search(SearchParams searchParams, Integer begin, Integer pageSize);

    Optional<GiftCertificate> findByName(String name);
}
