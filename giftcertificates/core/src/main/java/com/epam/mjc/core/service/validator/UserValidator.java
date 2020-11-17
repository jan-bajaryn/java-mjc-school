package com.epam.mjc.core.service.validator;

import com.epam.mjc.api.service.exception.UserValidatorException;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {
    public void validateId(Long id) {
        if (id == null || id < 1) {
            throw new UserValidatorException("user.wrong-id");
        }
    }

    public void validateIdNullable(Long userId) {
        if (userId != null && userId < 1) {
            throw new UserValidatorException("user.wrong-id");
        }
    }
}
