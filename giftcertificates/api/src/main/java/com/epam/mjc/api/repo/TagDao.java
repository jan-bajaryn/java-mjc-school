package com.epam.mjc.api.repo;

import com.epam.mjc.api.entity.Tag;

import java.util.List;

public interface TagDao {
    List<Tag> findAll();

    boolean create(Tag tag);

    boolean delete(Tag tag);
}
