package com.epam.mjc.api.service;

import com.epam.mjc.api.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagService {
    List<Tag> findAll();

    Tag createByName(String name);

    boolean deleteById(Long id);

    Optional<Tag> findById(Long id);

    List<Tag> findAllByGiftCertificateId(Long id);

    List<Tag> saveAll(List<Tag> tags);

    Tag save(Tag tag);
}
