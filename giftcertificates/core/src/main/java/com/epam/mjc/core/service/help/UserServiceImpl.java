package com.epam.mjc.core.service.help;

import com.epam.mjc.api.dao.UserDao;
import com.epam.mjc.api.domain.User;
import com.epam.mjc.api.service.exception.UserNotFoundException;
import com.epam.mjc.api.service.help.UserService;
import com.epam.mjc.core.service.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final UserValidator userValidator;

    @Autowired
    public UserServiceImpl(UserDao userDao, UserValidator userValidator) {
        this.userDao = userDao;
        this.userValidator = userValidator;
    }


    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public User findById(Long id) {
        userValidator.validateId(id);
        return userDao.findById(id).orElseThrow(() -> new UserNotFoundException("user.not-found"));
    }
}
