package com.epam.mjc.api.repo;

import com.epam.mjc.api.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagDao {
    List<Tag> findAll();

    Tag create(Tag tag);

    boolean delete(Tag tag);

    Optional<Tag> findById(Long id);

    List<Tag> findAllByGiftCertificateId(Long id);

    Optional<Tag> findByTagName(String name);
}
