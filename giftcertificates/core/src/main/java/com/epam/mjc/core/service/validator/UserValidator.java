package com.epam.mjc.core.service.validator;

import com.epam.mjc.api.domain.User;
import com.epam.mjc.api.service.exception.UserValidatorException;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {

    public static final int MAX_LENGTH = 255;
    private static final int MAX_LENGTH_PASSWORD = 255;

    public void validateId(Long id) {
        if (id == null || id < 1) {
            throw new UserValidatorException("user.wrong-id", id);
        }
    }

    public void validateIdNullable(Long userId) {
        if (userId != null && userId < 1) {
            throw new UserValidatorException("user.wrong-id", userId);
        }
    }

    public void validateUserBeforeCreate(User user) {
        validateNotNull(user);
        validateUsername(user);
        validatePassword(user);
    }

    private void validatePassword(User user) {
        if (user.getPassword() == null || user.getPassword().isEmpty() || user.getPassword().length() > MAX_LENGTH_PASSWORD) {
            throw new UserValidatorException("user.wrong-password");
        }
    }

    private void validateUsername(User user) {
        if (
                user.getUsername() == null
                        || user.getUsername().isEmpty()
                        || user.getUsername().length() > MAX_LENGTH
                        || !user.getUsername().matches("[\\S]+")
        ) {
            throw new UserValidatorException("user.wrong-username", user.getUsername());
        }
    }

    // TODO complete
    private void validateNotNull(User user) {
        if (user == null) {
            throw new UserValidatorException("user.null");
        }
    }
}
