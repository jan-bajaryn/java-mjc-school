package com.epam.service;

import com.epam.entity.Tag;
import com.epam.repo.TagDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagDao tagDao;

    public List<Tag> findAll() {
        return tagDao.findAll();
    }

}
