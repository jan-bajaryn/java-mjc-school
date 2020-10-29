package com.epam.mjc.api.repo;

import com.epam.mjc.api.entity.GiftCertificate;

import java.util.List;

public interface GiftCertificateDao {

    GiftCertificate create(GiftCertificate giftCertificate);

    boolean update(GiftCertificate giftCertificate);

    GiftCertificate findById(Long id);

    List<GiftCertificate> findAll();

    boolean delete(GiftCertificate giftCertificate);

    List<GiftCertificate> findAllByTagName(String tagName);

    List<GiftCertificate> findAllByPartNameAndPartDescription(String partName, String partDescription);

    // TODO ADD SORTED BY
}
