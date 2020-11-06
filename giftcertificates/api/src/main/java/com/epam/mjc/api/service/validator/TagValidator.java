package com.epam.mjc.api.service.validator;

public interface TagValidator {
    void validateTagName(String name);

    void validateTagId(Long id);
}
