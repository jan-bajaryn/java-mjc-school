package com.epam.mjc.api.service;

import com.epam.mjc.api.model.GiftCertificateModel;
import com.epam.mjc.api.model.GiftCertificateModelForCreate;
import com.epam.mjc.api.model.dto.GiftCertificateDto;
import org.springframework.hateoas.CollectionModel;

public interface GiftCertificateReturnService {
    GiftCertificateDto create(GiftCertificateModelForCreate giftCertificateModelForCreate);

    GiftCertificateDto findById(Long id);

    GiftCertificateDto update(Long id, GiftCertificateModel giftCertificateModel);

    void deleteById(Long id);

    CollectionModel<GiftCertificateDto> search(String tagName, String partName, String partDescription, String sort, Integer pageNumber, Integer pageSize);
}
