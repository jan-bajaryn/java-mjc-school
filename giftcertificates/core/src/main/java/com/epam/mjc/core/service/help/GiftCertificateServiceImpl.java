package com.epam.mjc.core.service.help;

import com.epam.mjc.api.dao.GiftCertificateRepo;
import com.epam.mjc.api.domain.GiftCertificate_;
import com.epam.mjc.api.service.exception.GiftCertificateValidatorException;
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
import com.epam.mjc.core.service.validator.PaginationValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertificateRepo giftCertificateRepo;
    private final TagService tagService;
    private final GiftCertificateValidator giftCertificateValidator;
    private final PaginationValidator paginationValidator;

    private static final Logger log = LoggerFactory.getLogger(GiftCertificateServiceImpl.class);

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateRepo giftCertificateRepo, TagService tagService, GiftCertificateValidator giftCertificateValidator, PaginationValidator paginationValidator) {
        this.giftCertificateRepo = giftCertificateRepo;
        this.tagService = tagService;
        this.giftCertificateValidator = giftCertificateValidator;
        this.paginationValidator = paginationValidator;
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

        giftCertificate.setCreateDate(LocalDateTime.now());
        giftCertificate.setLastUpdateDate(LocalDateTime.now());

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
        checkNull(certificate);
        giftCertificateValidator.validateGiftCertificateId(id);
        checkDuplicatedName(certificate, id);
        buildTagsByNames(certificate);
        GiftCertificate toUpdate = findById(id);

        copyFieldsToUpdate(certificate, toUpdate);
        giftCertificateValidator.validateGiftCertificate(toUpdate);

        return giftCertificateRepo.save(toUpdate);
    }

    private void checkDuplicatedName(GiftCertificate certificate, Long id) {
        if (certificate.getName() != null) {
            giftCertificateRepo.findByName(certificate.getName())
                    .ifPresent(g -> {
                        if (!g.getId().equals(id)) {
                            throw new GiftCertificateNameAlreadyExistsException("certificate.name-exists", certificate.getName());
                        }
                    });
        }
    }

    private void checkNull(GiftCertificate certificate) {
        if (certificate == null) {
            throw new GiftCertificateValidatorException("certificate.null");
        }
    }

    private void copyFieldsToUpdate(GiftCertificate certificate, GiftCertificate toUpdate) {
        toUpdate.setName(certificate.getName() == null ? toUpdate.getName() : certificate.getName());
        toUpdate.setDescription(certificate.getDescription() == null ? toUpdate.getDescription() : certificate.getDescription());
        toUpdate.setPrice(certificate.getPrice() == null ? toUpdate.getPrice() : certificate.getPrice());
        toUpdate.setDuration(certificate.getDuration() == null ? toUpdate.getDuration() : certificate.getDuration());
        toUpdate.setTags(certificate.getTags() == null ? toUpdate.getTags() : certificate.getTags());
        toUpdate.setLastUpdateDate(LocalDateTime.now());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GiftCertificate> search(SearchParams searchParams, Integer pageNumber, Integer pageSize) {

        paginationValidator.validatePagination(pageNumber, pageSize);
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize, buildSort(searchParams));

        return giftCertificateRepo.findAll(
                GiftCertificateSpecification.search(searchParams.getPartName(), searchParams.getPartDescription(), searchParams.getTagNames()),
                pageRequest
        );
    }

    private Sort buildSort(SearchParams searchParams) {
        Sort sort = Sort.unsorted();
        if (searchParams == null || searchParams.getSortParams() == null) {
            return sort.and(Sort.by(GiftCertificate_.LAST_UPDATE_DATE).descending());
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
        if (certificate.getTags() != null && !certificate.getTags().isEmpty()) {
            certificate.setTags(tagService.findOrCreateAll(certificate.getTags()));
        }
    }

}
