package com.epam.mjc.core.service.help;

import com.epam.mjc.api.dao.GiftCertificateDao;
import com.epam.mjc.api.domain.GiftCertificate;
import com.epam.mjc.api.domain.Tag;
import com.epam.mjc.api.service.exception.GiftCertificateAlreadyExists;
import com.epam.mjc.api.service.exception.GiftCertificateNameAlreadyExistsException;
import com.epam.mjc.api.service.exception.GiftCertificateNotFoundException;
import com.epam.mjc.api.service.help.GiftCertificateService;
import com.epam.mjc.api.service.help.TagService;
import com.epam.mjc.api.service.validator.GiftCertificateValidator;
import com.epam.mjc.api.util.SearchParams;
import com.epam.mjc.core.util.PaginationCalculator;
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
    private final PaginationCalculator paginationCalculator;

    private static final Logger log = LoggerFactory.getLogger(GiftCertificateServiceImpl.class);

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao, TagService tagService, GiftCertificateValidator giftCertificateValidator, PaginationCalculator paginationCalculator) {
        this.giftCertificateDao = giftCertificateDao;
        this.tagService = tagService;
        this.giftCertificateValidator = giftCertificateValidator;
        this.paginationCalculator = paginationCalculator;
    }

    @Override
    @Transactional(readOnly = true)
    public GiftCertificate findById(Long id) {
        giftCertificateValidator.validateGiftCertificateId(id);

        Optional<GiftCertificate> byId = giftCertificateDao.findById(id);
        if (!byId.isPresent()) {
            throw new GiftCertificateNotFoundException("certificate.not-found-id");
        }

        return byId.get();
    }


    @Override
    @Transactional
    public GiftCertificate create(GiftCertificate giftCertificate) {

        giftCertificateValidator.validateGiftCertificate(giftCertificate);
        checkIfNameExists(giftCertificate);


        GiftCertificate created = giftCertificateDao.create(giftCertificate);
        created.setTags(tagService.findOrCreateAll(created.getTags()));

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
    public void deleteById(Long id) {
        giftCertificateValidator.validateGiftCertificateId(id);
        GiftCertificate byId = findById(id);
        giftCertificateDao.delete(byId);
    }

    @Override
    @Transactional
    public void update(GiftCertificate certificate) {
        giftCertificateValidator.validateGiftCertificate(certificate);
        giftCertificateValidator.validateGiftCertificateId(certificate.getId());
        checkDuplicatedName(certificate);

        buildTagsByNames(certificate);
        GiftCertificate toUpdate = findById(certificate.getId());

        copyFieldsToUpdate(certificate, toUpdate);

        giftCertificateDao.update(toUpdate);
    }

    private void checkDuplicatedName(GiftCertificate certificate) {
        giftCertificateDao.findByName(certificate.getName())
                .ifPresent(g -> {
                    if (!g.getId().equals(certificate.getId())) {
                        throw new GiftCertificateNameAlreadyExistsException("certificate.name-exists");
                    }
                });
    }

    private void copyFieldsToUpdate(GiftCertificate certificate, GiftCertificate toUpdate) {
        toUpdate.setName(certificate.getName() == null ? toUpdate.getName() : certificate.getName());
        toUpdate.setDescription(certificate.getDescription() == null ? toUpdate.getDescription() : certificate.getDescription());
        toUpdate.setPrice(certificate.getPrice() == null ? toUpdate.getPrice() : certificate.getPrice());
        toUpdate.setDuration(certificate.getDuration() == null ? toUpdate.getDuration() : certificate.getDuration());
        toUpdate.setTags(certificate.getTags() == null ? toUpdate.getTags() : certificate.getTags());
    }

    @Override
    @Transactional(readOnly = true)
    public List<GiftCertificate> search(SearchParams searchParams, Integer pageNumber, Integer pageSize) {
        return giftCertificateDao.search(searchParams, paginationCalculator.calculateBegin(pageNumber, pageSize), pageSize);
    }

    @Override
    public GiftCertificate findByName(String name) {
        giftCertificateValidator.validateGiftCertificateName(name);
        return giftCertificateDao.findByName(name).orElseThrow(() -> new GiftCertificateNotFoundException("certificate.not-found-by-name"));
    }

    private void buildTagsByNames(GiftCertificate certificate) {
        if (certificate.getTags() != null) {
            List<Tag> result = certificate.getTags().stream()
                    .map(t -> {
                        Optional<Tag> byId = tagService.findByTagName(t.getName());
                        return byId.orElseGet(() -> tagService.createByName(t.getName()));

                    })
                    .collect(Collectors.toList());

            certificate.setTags(result);
        }
    }


}
