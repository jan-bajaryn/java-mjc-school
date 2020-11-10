package com.epam.mjc.core.service.validator;

import com.epam.mjc.api.service.exception.TagValidatorException;
import com.epam.mjc.api.service.validator.TagValidator;
import org.springframework.stereotype.Component;

@Component
public class TagValidatorImpl implements TagValidator {

    private static final int ID_MIN_VALUE = 0;
    private static final int NAME_MAX_LENGTH = 255;

    @Override
    public void validateTagName(String name) {
        if (name == null || name.isEmpty() || name.length() > NAME_MAX_LENGTH) {
            throw new TagValidatorException("tag.wrong-name");
        }
    }

    @Override
    public void validateTagId(Long id) {
        if (id == null || id < ID_MIN_VALUE) {
            throw new TagValidatorException("tag.wrong-id");
        }
    }
}
