package com.epam.service;

import com.epam.entity.Tag;
import com.epam.repo.TagDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

}
