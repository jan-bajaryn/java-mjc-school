package com.epam.mjc.core.service.validator;

import com.epam.mjc.api.service.exception.TagValidatorException;
import com.epam.mjc.api.service.validator.TagValidator;
import org.springframework.stereotype.Component;

@Component
public class TagValidatorImpl implements TagValidator {

    @Override
    public void validateTagName(String name) {
        if (name == null || name.length() > 255) {
            throw new TagValidatorException("Tag name is wrong.");
        }
    }

    @Override
    public void validateTagId(Long id) {
        if (id == null || id < 0) {
            throw new TagValidatorException("Id is wrong.");
        }
    }
}
