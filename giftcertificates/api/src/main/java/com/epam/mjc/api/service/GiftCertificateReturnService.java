package com.epam.mjc.api.service;

import com.epam.mjc.api.model.GiftCertificateModel;
import com.epam.mjc.api.model.GiftCertificateModelForCreate;
import com.epam.mjc.api.model.dto.GiftCertificateDto;
import com.epam.mjc.api.util.SearchParams;

import java.util.List;

public interface GiftCertificateReturnService {
    GiftCertificateDto create(GiftCertificateModelForCreate giftCertificateModelForCreate);
    GiftCertificateDto findById(Long id);
    boolean update(GiftCertificateModel giftCertificateModel);
    boolean deleteById(Long id);
    List<GiftCertificateDto> search(SearchParams searchParams);
}
