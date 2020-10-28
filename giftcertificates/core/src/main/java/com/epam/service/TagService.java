package com.epam.service;

import com.epam.entity.Tag;

import java.util.List;

public interface TagService {
    List<Tag> findAll();

    boolean createByName(String name);

    boolean deleteById(Long id);
}
