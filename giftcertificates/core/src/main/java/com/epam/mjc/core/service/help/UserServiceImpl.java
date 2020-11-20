package com.epam.mjc.core.service.help;

import com.epam.mjc.api.dao.UserDao;
import com.epam.mjc.api.domain.User;
import com.epam.mjc.api.service.exception.UserNotFoundException;
import com.epam.mjc.api.service.help.UserService;
import com.epam.mjc.core.service.validator.UserValidator;
import com.epam.mjc.core.util.PaginationCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserDao userDao;
    private final UserValidator userValidator;
    private final PaginationCalculator paginationCalculator;

    @Autowired
    public UserServiceImpl(UserDao userDao, UserValidator userValidator, PaginationCalculator paginationCalculator) {
        this.userDao = userDao;
        this.userValidator = userValidator;
        this.paginationCalculator = paginationCalculator;
    }


    @Override
    public List<User> findAll(Integer pageNumber, Integer pageSize) {
        log.debug("findAll: pageSize = {}", pageSize);
        return userDao.findAll(paginationCalculator.calculateBegin(pageNumber, pageSize), pageSize);
    }

    @Override
    public User findById(Long id) {
        userValidator.validateId(id);
        return userDao.findById(id).orElseThrow(() -> new UserNotFoundException("user.not-found"));
    }
}
