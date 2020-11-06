package com.epam.mjc.core.service;

import com.epam.mjc.api.domain.Tag;
import com.epam.mjc.api.dao.TagDao;
import com.epam.mjc.api.service.TagService;
import com.epam.mjc.api.service.exception.ServiceException;
import com.epam.mjc.api.service.validator.TagValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {
    private final TagDao tagDao;
    private final TagValidator tagValidator;
    private static final Logger log = LoggerFactory.getLogger(TagServiceImpl.class);

    public TagServiceImpl(final TagDao tagDao, TagValidator tagValidator) {
        this.tagDao = tagDao;
        this.tagValidator = tagValidator;
    }


    @Override
    public List<Tag> findAll() {
        return tagDao.findAll();
    }

    @Override
    public Tag createByName(String name) {

        tagValidator.validateTagName(name);

        log.debug("createByName entered");
        if (name == null) {
            log.debug("name == null");
            throw new ServiceException();
        }
        log.debug("name != null");
        return tagDao.create(Tag.builder().name(name).build());
    }

    @Override
    public boolean deleteById(Long id) {

        tagValidator.validateTagId(id);

        return tagDao.delete(Tag.builder().id(id).build());
    }

    @Override
    public Optional<Tag> findById(Long id) {
        tagValidator.validateTagId(id);
        return tagDao.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Tag> findAllByGiftCertificateId(Long id) {
        tagValidator.validateTagId(id);
        return tagDao.findAllByGiftCertificateId(id);
    }

    @Override
    @Transactional
    public List<Tag> findOrCreateAll(List<Tag> tags) {
        return tags.stream().map(this::findOrCreate).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Tag findOrCreate(Tag tag) {
        Optional<Tag> byTagName = findByTagName(tag.getName());
        return byTagName.orElseGet(() -> createByName(tag.getName()));
    }

    @Override
    public Optional<Tag> findByTagName(String name) {
        tagValidator.validateTagName(name);
        return tagDao.findByTagName(name);
    }

}
