package com.epam.mjc.core.service.help;

import com.epam.mjc.api.dao.GiftCertificateRepo;
import com.epam.mjc.core.dao.specification.GiftCertificateSpecification;
import com.epam.mjc.api.domain.GiftCertificate;
import com.epam.mjc.api.service.exception.GiftCertificateAlreadyExists;
import com.epam.mjc.api.service.exception.GiftCertificateNameAlreadyExistsException;
import com.epam.mjc.api.service.exception.GiftCertificateNotFoundException;
import com.epam.mjc.api.service.help.GiftCertificateService;
import com.epam.mjc.api.service.help.TagService;
import com.epam.mjc.api.service.validator.GiftCertificateValidator;
import com.epam.mjc.api.util.SearchParams;
import com.epam.mjc.api.util.sort.SortParam;
import com.epam.mjc.core.util.PaginationCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertificateRepo giftCertificateRepo;
    private final TagService tagService;
    private final GiftCertificateValidator giftCertificateValidator;
    private final PaginationCalculator paginationCalculator;

    private static final Logger log = LoggerFactory.getLogger(GiftCertificateServiceImpl.class);

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateRepo giftCertificateRepo, TagService tagService, GiftCertificateValidator giftCertificateValidator, PaginationCalculator paginationCalculator) {
        this.giftCertificateRepo = giftCertificateRepo;
        this.tagService = tagService;
        this.giftCertificateValidator = giftCertificateValidator;
        this.paginationCalculator = paginationCalculator;
    }

    @Override
    @Transactional(readOnly = true)
    public GiftCertificate findById(Long id) {
        giftCertificateValidator.validateGiftCertificateId(id);
        return giftCertificateRepo.findById(id)
                .orElseThrow(() -> new GiftCertificateNotFoundException("certificate.not-found-id", id));
    }


    @Override
    @Transactional
    public GiftCertificate create(GiftCertificate giftCertificate) {

        giftCertificateValidator.validateGiftCertificate(giftCertificate);
        checkIfNameExists(giftCertificate);

        GiftCertificate created = giftCertificateRepo.save(giftCertificate);
        created.setTags(tagService.findOrCreateAll(created.getTags()));

        return created;
    }

    private void checkIfNameExists(GiftCertificate giftCertificate) {
        Optional<GiftCertificate> byName = giftCertificateRepo.findByName(giftCertificate.getName());
        log.debug("create: byName.isPresent() = {}", byName.isPresent());

        if (byName.isPresent()) {
            throw new GiftCertificateAlreadyExists("certificate.exists", giftCertificate.getName());
        }
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        giftCertificateValidator.validateGiftCertificateId(id);
        GiftCertificate byId = findById(id);
        giftCertificateRepo.delete(byId);
    }

    @Override
    @Transactional
    public GiftCertificate update(Long id, GiftCertificate certificate) {
        giftCertificateValidator.validateGiftCertificate(certificate);
        giftCertificateValidator.validateGiftCertificateId(id);
        checkDuplicatedName(certificate, id);
        buildTagsByNames(certificate);
        GiftCertificate toUpdate = findById(id);

        copyFieldsToUpdate(certificate, toUpdate);

        return giftCertificateRepo.save(toUpdate);
    }

    private void checkDuplicatedName(GiftCertificate certificate, Long id) {
        giftCertificateRepo.findByName(certificate.getName())
                .ifPresent(g -> {
                    if (!g.getId().equals(id)) {
                        throw new GiftCertificateNameAlreadyExistsException("certificate.name-exists", certificate.getName());
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


        PageRequest pageRequest = PageRequest.of(paginationCalculator.calculateBegin(pageNumber, pageSize), pageSize, buildSort(searchParams));

        return giftCertificateRepo.findAll(
                GiftCertificateSpecification.search(searchParams.getPartName(), searchParams.getPartDescription(), searchParams.getTagNames()),
                pageRequest
        ).getContent();
    }

    private Sort buildSort(SearchParams searchParams) {
        Sort sort = Sort.unsorted();
        if (searchParams == null || searchParams.getSortParams() == null) {
            return sort;
        }
        List<SortParam> sortParams = searchParams.getSortParams().getSortParams();
        for (SortParam sortParam : sortParams) {
            sort = sort.and(Sort.by(sortParam.getFieldName().getColumnName()));
            if (sortParam.isAsc()) {
                sort = sort.ascending();
            } else {
                sort = sort.descending();
            }

        }
        return sort;
    }

    @Override
    public GiftCertificate findByName(String name) {
        giftCertificateValidator.validateGiftCertificateName(name);
        return giftCertificateRepo.findByName(name).orElseThrow(() -> new GiftCertificateNotFoundException("certificate.not-found-by-name", name));
    }

    private void buildTagsByNames(GiftCertificate certificate) {
        if (certificate.getTags() != null) {
            certificate.setTags(tagService.findOrCreateAll(certificate.getTags()));
        }
    }


}
