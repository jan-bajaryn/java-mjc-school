package com.epam.mjc.core.service;

import com.epam.mjc.api.entity.Tag;
import com.epam.mjc.api.repo.TagDao;
import com.epam.mjc.api.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagDao tagDao;

    @Override
    public List<Tag> findAll() {
        return tagDao.findAll();
    }

    @Override
    public boolean createByName(String name) {
        if (name == null) {
            return false;
        }

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

}
