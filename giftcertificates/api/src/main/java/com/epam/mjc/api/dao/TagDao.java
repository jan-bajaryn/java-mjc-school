package com.epam.mjc.api.dao;

import com.epam.mjc.api.domain.Tag;

import java.util.List;
import java.util.Optional;

public interface TagDao {
    List<Tag> findAll(Integer begin, Integer pageSize);

    Tag create(Tag tag);

    boolean delete(Tag tag);

    Optional<Tag> findById(Long id);

    Optional<Tag> findByTagName(String name);

    Tag findMostPopularTagIdOfUserHigherCostOrders();
}
