package com.epam.mjc.core.controller.mapper;

import com.epam.mjc.api.domain.GiftCertificate;
import com.epam.mjc.api.model.GiftCertificateModelForCreate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GiftCertificateMapper {

    private final TagMapper tagMapper;

    @Autowired
    public GiftCertificateMapper(TagMapper tagMapper) {
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
}
