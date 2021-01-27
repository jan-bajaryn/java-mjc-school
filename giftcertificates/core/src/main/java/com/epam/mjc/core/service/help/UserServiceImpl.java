package com.epam.mjc.core.service.help;

import com.epam.mjc.api.dao.UserRepo;
import com.epam.mjc.api.domain.Role;
import com.epam.mjc.api.domain.User;
import com.epam.mjc.api.service.exception.UserAlreadyExistsException;
import com.epam.mjc.api.service.exception.UserNotFoundException;
import com.epam.mjc.api.service.help.UserService;
import com.epam.mjc.core.service.auth.CustomPasswordEncoder;
import com.epam.mjc.core.service.validator.PaginationValidator;
import com.epam.mjc.core.service.validator.UserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepo userRepo;
    private final UserValidator userValidator;
    private final CustomPasswordEncoder customPasswordEncoder;
    private final PaginationValidator paginationValidator;

    @Autowired
    public UserServiceImpl(UserRepo userRepo, UserValidator userValidator, CustomPasswordEncoder customPasswordEncoder, PaginationValidator paginationValidator) {
        this.userRepo = userRepo;
        this.userValidator = userValidator;
        this.customPasswordEncoder = customPasswordEncoder;
        this.paginationValidator = paginationValidator;
    }


    @Override
    public List<User> findAll(Integer pageNumber, Integer pageSize) {
        log.debug("findAll: pageSize = {}", pageSize);
        paginationValidator.validatePagination(pageNumber, pageSize);
        return userRepo.findAll(PageRequest.of(pageNumber - 1, pageSize)).getContent();
    }

    @Override
    public User findById(Long id) {
        userValidator.validateId(id);
        return userRepo.findById(id).orElseThrow(() -> new UserNotFoundException("user.not-found", id));
    }

    @Override
    @Transactional
    public User signUp(User user) {
        userValidator.validateUserBeforeCreate(user);

        if (user.getName() != null && user.getName().isEmpty()) {
            user.setName(null);
        }

        checkUsernameDuplicate(user);
        user.setRole(Role.USER);
        user.setPassword(customPasswordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    private void checkUsernameDuplicate(User user) {
        User byUsername = userRepo.findByUsername(user.getUsername());
        if (byUsername != null) {
            throw new UserAlreadyExistsException("user.username-exists", user.getUsername());
        }
    }
}
