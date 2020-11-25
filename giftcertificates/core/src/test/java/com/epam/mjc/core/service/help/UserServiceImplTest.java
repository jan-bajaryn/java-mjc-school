package com.epam.mjc.core.service.help;

import com.epam.mjc.api.dao.UserDao;
import com.epam.mjc.api.domain.User;
import com.epam.mjc.api.service.exception.PaginationException;
import com.epam.mjc.api.service.exception.UserNotFoundException;
import com.epam.mjc.api.service.exception.UserValidatorException;
import com.epam.mjc.core.service.validator.UserValidator;
import com.epam.mjc.core.util.PaginationCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private static final int PAGE_SIZE = 5;
    private static final int PAGE_NUMBER = 1;
    private static final String USERNAME = "username";
    private static final long ID = 1L;

    @Mock
    private UserDao userDao;

    @Mock
    private UserValidator userValidator;

    @Mock
    private PaginationCalculator paginationCalculator;

    @InjectMocks
    private UserServiceImpl userService;


    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(ID);
        user.setUsername(USERNAME);
    }

    @Test
    void findAllpaginationCalculatorThrowsExceptionThanThrowException() {
        when(paginationCalculator.calculateBegin(PAGE_NUMBER, PAGE_SIZE)).thenThrow(PaginationException.class);
        assertThrows(
                PaginationException.class,
                () -> userService.findAll(PAGE_NUMBER, PAGE_SIZE)
        );
    }

    @Test
    void findById() {
        when(userDao.findById(ID)).thenReturn(Optional.of(user));

        User actual = userService.findById(ID);
        assertEquals(user, actual);

        verify(userValidator).validateId(ID);

    }

    @Test
    void findByIdValidatorException() {
        doThrow(UserValidatorException.class).when(userValidator).validateId(ID);

        assertThrows(UserValidatorException.class,
                () -> userService.findById(ID));
    }

    @Test
    void findByIdNotFoundException() {
        when(userDao.findById(ID)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> userService.findById(ID));
    }
}