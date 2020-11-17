package com.epam.mjc.api.service;

import com.epam.mjc.api.model.GiftCertificateModel;
import com.epam.mjc.api.model.GiftCertificateModelForCreate;
import com.epam.mjc.api.model.dto.GiftCertificateDto;

import java.util.List;

public interface GiftCertificateReturnService {
    GiftCertificateDto create(GiftCertificateModelForCreate giftCertificateModelForCreate);
    GiftCertificateDto findById(Long id);
    void update(GiftCertificateModel giftCertificateModel);
    void deleteById(Long id);
    List<GiftCertificateDto> search(String tagName, String partName, String partDescription, String sort,Integer pageNumber,Integer pageSize);
}
