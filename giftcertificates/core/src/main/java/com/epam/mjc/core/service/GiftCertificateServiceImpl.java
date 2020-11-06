package com.epam.mjc.core.service;

import com.epam.mjc.api.domain.GiftCertificate;
import com.epam.mjc.api.domain.Tag;
import com.epam.mjc.api.dao.GiftCertificateDao;
import com.epam.mjc.api.service.GiftCertificateService;
import com.epam.mjc.api.service.TagService;
import com.epam.mjc.api.service.exception.ServiceException;
import com.epam.mjc.api.service.validator.GiftCertificateValidator;
import com.epam.mjc.api.util.SearchParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertificateDao giftCertificateDao;
    private final TagService tagService;
    private final GiftCertificateValidator giftCertificateValidator;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao, TagService tagService, GiftCertificateValidator giftCertificateValidator) {
        this.giftCertificateDao = giftCertificateDao;
        this.tagService = tagService;
        this.giftCertificateValidator = giftCertificateValidator;
    }

    @Override
    @Transactional(readOnly = true)
    public List<GiftCertificate> findAll() {
        List<GiftCertificate> all = giftCertificateDao.findAll();

        buildAllRelations(all);

        return all;
    }

    @Override
    @Transactional(readOnly = true)
    public GiftCertificate findById(Long id) {
        giftCertificateValidator.validateGiftCertificateId(id);

        GiftCertificate byId = giftCertificateDao.findById(id);
        buildRelations(byId);
        return byId;
    }


    @Override
    @Transactional
    public GiftCertificate create(GiftCertificate giftCertificate) {

        giftCertificateValidator.validateGiftCertificate(giftCertificate);

        GiftCertificate created = giftCertificateDao.create(giftCertificate);
        tagService.findOrCreateAll(created.getTags());
        for (Tag tag : created.getTags()) {
            giftCertificateDao.addTag(giftCertificate, tag);
        }
        return created;
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
        giftCertificateValidator.validateGiftCertificateId(id);
        GiftCertificate byId = giftCertificateDao.findById(id);
        return giftCertificateDao.delete(byId);
    }

    @Override
    @Transactional
    public boolean update(GiftCertificate certificate) {

        giftCertificateValidator.validateGiftCertificate(certificate);

        buildTagsByNames(certificate);

        GiftCertificate toUpdate = findById(certificate.getId());

        List<Tag> prevTags = toUpdate.getTags();

        toUpdate.setName(certificate.getName() == null ? null : certificate.getName());
        toUpdate.setDescription(certificate.getDescription() == null ? null : certificate.getDescription());
        toUpdate.setPrice(certificate.getPrice() == null ? null : certificate.getPrice());
        toUpdate.setDuration(certificate.getDuration() == null ? null : certificate.getDuration());
        toUpdate.setTags(certificate.getTags() == null ? null : certificate.getTags());

        return doUpdate(toUpdate, prevTags);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GiftCertificate> search(SearchParams searchParams) {
        return giftCertificateDao.search(searchParams);
    }

    private void buildTagsByNames(GiftCertificate certificate) {
        List<Tag> result = certificate.getTags().stream()
                .map(t -> {
                    Optional<Tag> byId = tagService.findByTagName(t.getName());
                    return byId.orElseGet(() -> tagService.createByName(t.getName()));

                })
                .collect(Collectors.toList());

        certificate.setTags(result);
    }


    public boolean doUpdate(GiftCertificate toUpdate, List<Tag> prevTags) {
        boolean update = giftCertificateDao.update(toUpdate);
        if (!update) {
            throw new ServiceException();
        }
        tagsForDelete(prevTags, toUpdate.getTags()).forEach(t -> giftCertificateDao.deleteTag(toUpdate, t));
        tagsForAdd(prevTags, toUpdate.getTags()).forEach(t -> giftCertificateDao.addTag(toUpdate, t));
        return true;
    }


    private List<Tag> tagsForAdd(List<Tag> prevTags, List<Tag> tags) {
        return tags.stream()
                .filter(prevTags::contains)
                .collect(Collectors.toList());
    }

    private List<Tag> tagsForDelete(List<Tag> prevTags, List<Tag> tags) {
        return tags.stream()
                .filter(t -> !prevTags.contains(t))
                .collect(Collectors.toList());
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
