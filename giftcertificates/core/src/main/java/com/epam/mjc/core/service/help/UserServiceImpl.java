package com.epam.mjc.core.service.help;

import com.epam.mjc.api.dao.UserRepo;
import com.epam.mjc.api.domain.User;
import com.epam.mjc.api.service.exception.UserNotFoundException;
import com.epam.mjc.api.service.help.UserService;
import com.epam.mjc.core.service.validator.UserValidator;
import com.epam.mjc.core.util.PaginationCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepo userRepo;
    private final UserValidator userValidator;
    private final PaginationCalculator paginationCalculator;

    @Autowired
    public UserServiceImpl(UserRepo userRepo, UserValidator userValidator, PaginationCalculator paginationCalculator) {
        this.userRepo = userRepo;
        this.userValidator = userValidator;
        this.paginationCalculator = paginationCalculator;
    }


    @Override
    public List<User> findAll(Integer pageNumber, Integer pageSize) {
        log.debug("findAll: pageSize = {}", pageSize);
        return userRepo.findAll(PageRequest.of(paginationCalculator.calculateBegin(pageNumber, pageSize), pageSize)).getContent();
    }

    @Override
    public User findById(Long id) {
        userValidator.validateId(id);
        return userRepo.findById(id).orElseThrow(() -> new UserNotFoundException("user.not-found", id));
    }
}
