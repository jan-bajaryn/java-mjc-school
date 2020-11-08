package com.epam.mjc.core.service.help;

import com.epam.mjc.api.dao.TagDao;
import com.epam.mjc.api.domain.Tag;
import com.epam.mjc.api.service.help.TagService;
import com.epam.mjc.api.service.exception.TagAlreadyExistsException;
import com.epam.mjc.api.service.exception.TagNotFoundException;
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

        Optional<Tag> byTagName = findByTagName(name);
        log.debug("createByName: byTagName.isPresent() = {}", byTagName.isPresent());
        if (byTagName.isPresent()) {
            throw new TagAlreadyExistsException("tag.exists");
        }

        return tagDao.create(Tag.builder().name(name).build());
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {

        tagValidator.validateTagId(id);

        Tag toDelete = findById(id);
        return tagDao.delete(toDelete);
    }

    @Override
    public Tag findById(Long id) {
        tagValidator.validateTagId(id);
        Optional<Tag> byId = tagDao.findById(id);
        if (!byId.isPresent()) {
            throw new TagNotFoundException("tag.not-found-id");
        }
        return byId.get();
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

// waiting for batchUpdate with generated keys
//        List<Tag> existing = tagDao.findAllExistingByNames(tags);
//        List<Tag> toAdd = new ArrayList<>(tags);
//        toAdd.removeAll(existing);
//        createAllByName(toAdd);

        return tags.stream().map(this::findOrCreate).collect(Collectors.toList());
    }

    private void createAllByName(List<Tag> toAdd) {
//        tagDao.createAll(toAdd);
        for (Tag tag : toAdd) {
            createByName(tag.getName());
        }
    }

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
