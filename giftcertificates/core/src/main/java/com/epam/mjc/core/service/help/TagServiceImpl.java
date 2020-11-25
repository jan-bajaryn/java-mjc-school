package com.epam.mjc.core.service.help;

import com.epam.mjc.api.dao.TagDao;
import com.epam.mjc.api.domain.Tag;
import com.epam.mjc.api.service.exception.TagAlreadyExistsException;
import com.epam.mjc.api.service.exception.TagNotFoundException;
import com.epam.mjc.api.service.help.TagService;
import com.epam.mjc.api.service.validator.TagValidator;
import com.epam.mjc.core.util.PaginationCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TagServiceImpl implements TagService {
    private final TagDao tagDao;
    private final TagValidator tagValidator;
    private final PaginationCalculator paginationCalculator;


    private static final Logger log = LoggerFactory.getLogger(TagServiceImpl.class);

    public TagServiceImpl(final TagDao tagDao, TagValidator tagValidator, PaginationCalculator paginationCalculator) {
        this.tagDao = tagDao;
        this.tagValidator = tagValidator;
        this.paginationCalculator = paginationCalculator;
    }


    @Override
    public List<Tag> findAll(Integer pageNumber, Integer pageSize) {
        return tagDao.findAll(paginationCalculator.calculateBegin(pageNumber, pageSize), pageSize);
    }

    @Override
    public Tag createByName(String name) {

        tagValidator.validateTagName(name);

        Optional<Tag> byTagName = findByTagName(name);
        log.debug("createByName: byTagName.isPresent() = {}", byTagName.isPresent());
        if (byTagName.isPresent()) {
            throw new TagAlreadyExistsException("tag.exists", name);
        }

        return tagDao.create(Tag.builder().name(name).build());
    }

    @Override
    @Transactional
    public void deleteById(Long id) {

        tagValidator.validateTagId(id);

        Tag toDelete = findById(id);
        tagDao.delete(toDelete);
    }

    @Override
    public Tag findById(Long id) {
        tagValidator.validateTagId(id);
        Optional<Tag> byId = tagDao.findById(id);
        if (!byId.isPresent()) {
            throw new TagNotFoundException("tag.not-found-id", id);
        }
        return byId.get();
    }


    @Override
    @Transactional
    public List<Tag> findOrCreateAll(List<Tag> tags) {

        validateTagNames(tags);

        List<Tag> existing = tagDao.findAllExistingByNames(tags);

        List<Tag> toAdd = new ArrayList<>(tags);
        toAdd.removeIf(
                t -> existing.stream().anyMatch(e -> e.getName().equals(t.getName()))
        );
        createAll(toAdd);

        return Stream.concat(toAdd.stream(), existing.stream())
                .sorted(Comparator.comparingInt(a -> findIndex(tags, a)))
                .collect(Collectors.toList());
    }

    private void validateTagNames(List<Tag> tags) {
        tags.forEach(t -> tagValidator.validateTagName(t.getName()));
    }

    private int findIndex(List<Tag> tags, Tag b) {
        for (int i = 0; i < tags.size(); i++) {
            if (tags.get(i).getName().equals(b.getName())) {
                return i;
            }
        }
        throw new IllegalArgumentException();
    }

    private void createAll(List<Tag> toAdd) {
        tagDao.createAll(toAdd);
    }

//    @Transactional
//    public Tag findOrCreate(Tag tag) {
//        Optional<Tag> byTagName = findByTagName(tag.getName());
//        return byTagName.orElseGet(() -> createByName(tag.getName()));
//    }

    @Override
    public Optional<Tag> findByTagName(String name) {
        tagValidator.validateTagName(name);
        return tagDao.findByTagName(name);
    }

    @Override
    public Tag findMostPopularTagOfUserHigherCostOrders() {
        return tagDao.findMostPopularTagIdOfUserHigherCostOrders();
    }

}
