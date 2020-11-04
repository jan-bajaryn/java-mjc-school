package com.epam.mjc.core.service;

import com.epam.mjc.api.entity.Tag;
import com.epam.mjc.api.repo.TagDao;
import com.epam.mjc.api.service.TagService;
import com.epam.mjc.core.service.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagDao tagDao;

    private static final Logger log = LoggerFactory.getLogger(TagServiceImpl.class);

    @Override
    public List<Tag> findAll() {
        return tagDao.findAll();
    }

    @Override
    public Tag createByName(String name) {
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
        if (id == null) {
            return false;
        }

        return tagDao.delete(Tag.builder().id(id).build());
    }

    @Override
    public Optional<Tag> findById(Long id) {
        return tagDao.findById(id);
    }

    @Override
    public List<Tag> findAllByGiftCertificateId(Long id) {
        return tagDao.findAllByGiftCertificateId(id);
    }

    @Override
    public List<Tag> findOrCreateAll(List<Tag> tags) {
        return tags.stream()
                .map(this::findOrCreate)
                .collect(Collectors.toList());
    }

    @Override
    public Tag findOrCreate(Tag tag) {
        Optional<Tag> byTagName = findByTagName(tag.getName());
        return byTagName.orElseGet(() -> createByName(tag.getName()));
    }

    @Override
    public Optional<Tag> findByTagName(String name) {
        return tagDao.findByTagName(name);
    }

}
