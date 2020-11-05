package com.epam.mjc.core.service;

import com.epam.mjc.api.domain.GiftCertificate;
import com.epam.mjc.api.domain.Tag;
import com.epam.mjc.api.dao.GiftCertificateDao;
import com.epam.mjc.api.service.GiftCertificateService;
import com.epam.mjc.api.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertificateDao giftCertificateDao;
    private final TagService tagService;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao, TagService tagService) {
        this.giftCertificateDao = giftCertificateDao;
        this.tagService = tagService;
    }

    @Override
    public List<GiftCertificate> findAll() {
        List<GiftCertificate> all = giftCertificateDao.findAll();

        buildAllRelations(all);

        return all;
    }

    @Override
    public GiftCertificate findById(Long id) {
        GiftCertificate byId = giftCertificateDao.findById(id);
        buildRelations(byId);
        return byId;
    }


    @Override
    @Transactional
    public GiftCertificate create(GiftCertificate giftCertificate) {
        GiftCertificate created = giftCertificateDao.create(giftCertificate);
        tagService.findOrCreateAll(created.getTags());
        for (Tag tag : created.getTags()) {
            giftCertificateDao.addTag(giftCertificate, tag);
        }
        return created;
    }

    @Override
    public boolean deleteById(Long id) {
        return giftCertificateDao.delete(GiftCertificate.builder().id(id).build());
    }

    private void buildAllRelations(List<GiftCertificate> all) {
        for (GiftCertificate giftCertificate : all) {
            buildRelations(giftCertificate);
        }
    }

    private void buildRelations(GiftCertificate giftCertificate) {
        giftCertificate.setTags(tagService.findAllByGiftCertificateId(giftCertificate.getId()));
    }
}
