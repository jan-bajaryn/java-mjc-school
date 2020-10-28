package com.epam.repo;

import com.epam.entity.Tag;

import java.util.List;

public interface TagDao {
     List<Tag> findAll();
}
