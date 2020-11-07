package com.epam.mjc.api.controller.mapper;

import com.epam.mjc.api.domain.GiftCertificate;
import com.epam.mjc.api.model.GiftCertificateModel;
import com.epam.mjc.api.model.GiftCertificateModelForCreate;

public interface GiftCertificateMapper {

    GiftCertificate toGiftCertificate(GiftCertificateModelForCreate forCreate);
    GiftCertificate toGiftCertificate(GiftCertificateModel model);


}
