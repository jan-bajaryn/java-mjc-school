package com.epam.mjc.core.service;

import com.epam.mjc.api.dao.GiftCertificateDao;
import com.epam.mjc.api.domain.GiftCertificate;
import com.epam.mjc.api.domain.Tag;
import com.epam.mjc.api.service.GiftCertificateService;
import com.epam.mjc.api.service.TagService;
import com.epam.mjc.api.service.exception.GiftCertificateAlreadyExists;
import com.epam.mjc.api.service.exception.GiftCertificateNotFoundException;
import com.epam.mjc.api.service.exception.UnexpectedServiceException;
import com.epam.mjc.api.service.validator.GiftCertificateValidator;
import com.epam.mjc.api.util.SearchParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(GiftCertificateServiceImpl.class);

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

        Optional<GiftCertificate> byId = giftCertificateDao.findById(id);
        if (!byId.isPresent()) {
            throw new GiftCertificateNotFoundException("certificate.not-found-id");
        }
        GiftCertificate giftCertificate = byId.get();

        buildRelations(giftCertificate);
        return giftCertificate;
    }


    @Override
    @Transactional
    public GiftCertificate create(GiftCertificate giftCertificate) {

        giftCertificateValidator.validateGiftCertificate(giftCertificate);

        checkIfNameExists(giftCertificate);

        GiftCertificate created = giftCertificateDao.create(giftCertificate);
        tagService.findOrCreateAll(created.getTags());

        giftCertificateDao.addTags(giftCertificate,created.getTags());

        return created;
    }

    private void checkIfNameExists(GiftCertificate giftCertificate) {
        Optional<GiftCertificate> byName = giftCertificateDao.findByName(giftCertificate.getName());
        log.debug("create: byName.isPresent() = {}", byName.isPresent());

        if (byName.isPresent()) {
            throw new GiftCertificateAlreadyExists("certificate.exists");
        }
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
        giftCertificateValidator.validateGiftCertificateId(id);
        GiftCertificate byId = findById(id);
        return giftCertificateDao.delete(byId);
    }

    @Override
    @Transactional
    public boolean update(GiftCertificate certificate) {

        giftCertificateValidator.validateGiftCertificate(certificate);
        buildTagsByNames(certificate);
        GiftCertificate toUpdate = findById(certificate.getId());
        List<Tag> prevTags = toUpdate.getTags();
        copyFieldsToUpdate(certificate, toUpdate);

        return doUpdate(toUpdate, prevTags);
    }

    private void copyFieldsToUpdate(GiftCertificate certificate, GiftCertificate toUpdate) {
        toUpdate.setName(certificate.getName() == null ? null : certificate.getName());
        toUpdate.setDescription(certificate.getDescription() == null ? null : certificate.getDescription());
        toUpdate.setPrice(certificate.getPrice() == null ? null : certificate.getPrice());
        toUpdate.setDuration(certificate.getDuration() == null ? null : certificate.getDuration());
        toUpdate.setTags(certificate.getTags() == null ? null : certificate.getTags());
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
        // TODO test this case
        if (!update) {
            throw new UnexpectedServiceException("certificate.unexpected.cant-update");
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
