package com.epam.mjc.api.service.help;

import com.epam.mjc.api.domain.GiftCertificate;
import com.epam.mjc.api.util.SearchParams;

import java.util.List;

public interface GiftCertificateService {
    List<GiftCertificate> findAll();

    GiftCertificate findById(Long id);

    GiftCertificate create(GiftCertificate giftCertificate);

    boolean deleteById(Long id);

    boolean update(GiftCertificate toGiftCertificate);

    List<GiftCertificate> search(SearchParams searchParams);
}
