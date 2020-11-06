package com.epam.mjc.core.controller.mapper;

import com.epam.mjc.api.controller.mapper.GiftCertificateMapper;
import com.epam.mjc.api.domain.GiftCertificate;
import com.epam.mjc.api.model.GiftCertificateModel;
import com.epam.mjc.api.model.GiftCertificateModelForCreate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GiftCertificateMapperImpl implements GiftCertificateMapper {

    private final TagMapperImpl tagMapper;

    @Autowired
    public GiftCertificateMapperImpl(TagMapperImpl tagMapper) {
        this.tagMapper = tagMapper;
    }

    public GiftCertificate toGiftCertificate(GiftCertificateModelForCreate forCreate) {
        GiftCertificate giftCertificate = new GiftCertificate();

        giftCertificate.setDescription(forCreate.getDescription());
        giftCertificate.setName(forCreate.getName());
        giftCertificate.setPrice(forCreate.getPrice());
        giftCertificate.setDuration(forCreate.getDuration());
        giftCertificate.setTags(tagMapper.toTag(forCreate.getTags()));

        return giftCertificate;
    }

    public GiftCertificate toGiftCertificate(GiftCertificateModel model) {
        GiftCertificate giftCertificate = new GiftCertificate();

        giftCertificate.setId(model.getId());
        giftCertificate.setDescription(model.getDescription());
        giftCertificate.setName(model.getName());
        giftCertificate.setPrice(model.getPrice());
        giftCertificate.setDuration(model.getDuration());


        giftCertificate.setTags(tagMapper.toTag(model.getTags()));
        return giftCertificate;
    }
}
