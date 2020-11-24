package com.epam.mjc.core.service.help;

import com.epam.mjc.api.domain.Tag;
import com.epam.mjc.api.service.exception.TagNotFoundException;
import com.epam.mjc.api.service.exception.TagValidatorException;
import com.epam.mjc.api.service.validator.TagValidator;
import com.epam.mjc.core.dao.TagDaoImpl;
import com.epam.mjc.core.util.PaginationCalculator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    private static final Long TAG_ID = 1L;
    private static final String TAG_NAME = "NAME";

    @Mock
    private TagDaoImpl mockTagDao;

    @Mock
    private TagValidator validator;

    @Mock
    private PaginationCalculator paginationCalculator;

    @InjectMocks
    private TagServiceImpl tagServiceImpl;

    @Test
    void findById() {
        Tag expected = mock(Tag.class);

        when(mockTagDao.findById(TAG_ID)).thenReturn(Optional.of(expected));

        Tag actual = tagServiceImpl.findById(TAG_ID);
        assertEquals(expected, actual);

        verify(validator).validateTagId(TAG_ID);

    }

    @Test
    void findByIdValidatorException() {
        doThrow(TagValidatorException.class).when(validator).validateTagId(TAG_ID);

        assertThrows(TagValidatorException.class,
                () -> tagServiceImpl.findById(TAG_ID));
    }

    @Test
    void findByIdNotFoundException() {
        when(mockTagDao.findById(TAG_ID)).thenThrow(TagNotFoundException.class);

        assertThrows(TagNotFoundException.class,
                () -> tagServiceImpl.findById(TAG_ID));
    }

    @Test
    void createByName() {
        Tag expected = new Tag(1L,TAG_NAME,new ArrayList<>());

        when(mockTagDao.findByTagName(TAG_NAME)).thenReturn(Optional.empty());
        when(mockTagDao.create(any(Tag.class))).thenReturn(expected);

        Tag actual = tagServiceImpl.createByName(TAG_NAME);
        assertSame(expected, actual);
    }

    @Test
    void deleteById() {
    }


    @Test
    void findOrCreateAll() {
    }

    @Test
    void findByTagName() {
    }

    @Test
    void findMostPopularTagOfUserHigherCostOrders() {
    }
}