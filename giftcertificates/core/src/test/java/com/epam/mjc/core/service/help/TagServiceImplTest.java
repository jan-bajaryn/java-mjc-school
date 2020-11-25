package com.epam.mjc.core.service.help;

import com.epam.mjc.api.domain.Tag;
import com.epam.mjc.api.service.exception.PaginationException;
import com.epam.mjc.api.service.exception.TagAlreadyExistsException;
import com.epam.mjc.api.service.exception.TagNotFoundException;
import com.epam.mjc.api.service.exception.TagValidatorException;
import com.epam.mjc.core.dao.TagDaoImpl;
import com.epam.mjc.core.service.validator.TagValidatorImpl;
import com.epam.mjc.core.util.PaginationCalculator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    private static final Long ID = 1L;
    private static final String NAME = "NAME";
    private static final String NAME_2 = "NAME2";
    private static final String NAME_3 = "NAME3";
    private static final int PAGE_SIZE = 5;
    private static final int PAGE_NUMBER = 1;

    @Mock
    private TagDaoImpl mockTagDao;

    @Mock
    private TagValidatorImpl tagValidator;

    @Mock
    private PaginationCalculator paginationCalculator;

    @InjectMocks
    private TagServiceImpl tagServiceImpl;


    @Test
    public void findAllpaginationCalculatorThrowsExceptionThanThrowException() {
        when(paginationCalculator.calculateBegin(PAGE_NUMBER, PAGE_SIZE)).thenThrow(PaginationException.class);
        assertThrows(
                PaginationException.class,
                () -> tagServiceImpl.findAll(PAGE_NUMBER, PAGE_SIZE)
        );
    }

    @Test
    void findById() {
        Tag expected = mock(Tag.class);

        when(mockTagDao.findById(ID)).thenReturn(Optional.of(expected));

        Tag actual = tagServiceImpl.findById(ID);
        assertEquals(expected, actual);

        verify(tagValidator).validateTagId(ID);

    }

    @Test
    void findByIdValidatorException() {
        doThrow(TagValidatorException.class).when(tagValidator).validateTagId(ID);

        assertThrows(TagValidatorException.class,
                () -> tagServiceImpl.findById(ID));
    }

    @Test
    void findByIdNotFoundException() {
        when(mockTagDao.findById(ID)).thenReturn(Optional.empty());

        assertThrows(TagNotFoundException.class,
                () -> tagServiceImpl.findById(ID));
    }

    @Test
    void createByName() {
        Tag expected = new Tag(ID, NAME, new ArrayList<>());

        when(mockTagDao.findByTagName(NAME)).thenReturn(Optional.empty());
        when(mockTagDao.create(any(Tag.class))).thenReturn(expected);

        Tag actual = tagServiceImpl.createByName(NAME);
        assertSame(expected, actual);

        verify(tagValidator, atLeastOnce()).validateTagName(NAME);
        verify(mockTagDao).findByTagName(NAME);
        verify(mockTagDao).create(any(Tag.class));
    }

    @Test
    void createByNameValidatorThrowsExceptionThanServiceThrowsException() {

        doThrow(TagValidatorException.class).when(tagValidator).validateTagName(NAME);

        assertThrows(TagValidatorException.class, () -> tagServiceImpl.createByName(NAME));

        verify(tagValidator).validateTagName(NAME);
        verify(mockTagDao, never()).findByTagName(NAME);
        verify(mockTagDao, never()).create(any(Tag.class));
    }


    @Test
    void createByNameNameAlreadyExistsThanThrowsTagAlreadyExistsException() {

        Tag returnTag = new Tag(null, null, null);
        when(mockTagDao.findByTagName(NAME)).thenReturn(Optional.of(returnTag));

        assertThrows(TagAlreadyExistsException.class, () -> tagServiceImpl.createByName(NAME));

        verify(tagValidator, atLeastOnce()).validateTagName(NAME);
        verify(mockTagDao).findByTagName(NAME);
        verify(mockTagDao, never()).create(any(Tag.class));
    }

    @Test
    void deleteById() {
        Tag tag = new Tag(ID, null, null);
        when(mockTagDao.findById(ID)).thenReturn(Optional.of(tag));

        assertDoesNotThrow(() -> tagServiceImpl.deleteById(ID));

        verify(tagValidator, atLeastOnce()).validateTagId(ID);
        verify(mockTagDao).findById(ID);
        verify(mockTagDao).delete(tag);
    }

    @Test
    void deleteByIdValidatorThrowsExceptionThantServiceThrowsException() {
        doThrow(TagValidatorException.class).when(tagValidator).validateTagId(ID);

        assertThrows(TagValidatorException.class, () -> tagServiceImpl.deleteById(ID));

        verify(tagValidator).validateTagId(ID);
        verify(mockTagDao, never()).findById(ID);
        verify(mockTagDao, never()).delete(any(Tag.class));
    }

    @Test
    void deleteByIdNotFoundThantTrowsTagNotFoundException() {

        when(mockTagDao.findById(ID)).thenReturn(Optional.empty());

        assertThrows(TagNotFoundException.class, () -> tagServiceImpl.deleteById(ID));

        verify(tagValidator, atLeastOnce()).validateTagId(ID);
        verify(mockTagDao).findById(ID);
        verify(mockTagDao, never()).delete(any(Tag.class));
    }


    @Test
    void findOrCreateAll() {
        Tag tag1 = new Tag(null, NAME, null);
        Tag tag2 = new Tag(null, NAME_2, null);
        Tag tag3 = new Tag(null, NAME_3, null);
        List<Tag> tags = new ArrayList<>(Arrays.asList(tag1, tag2, tag3));
        doNothing().when(mockTagDao).createAll(tags);
        doReturn(new ArrayList<>()).when(mockTagDao).findAllExistingByNames(tags);

        assertEquals(tags, tagServiceImpl.findOrCreateAll(tags));

        verify(tagValidator, times(tags.size())).validateTagName(anyString());
        verify(mockTagDao).findAllExistingByNames(tags);
        verify(mockTagDao).createAll(tags);
    }

    @Test
    void findOrCreateAllValidatorExceptionThantThrowsValidatorException() {
        Tag tag1 = new Tag(null, NAME, null);
        Tag tag2 = new Tag(null, NAME_2, null);
        Tag tag3 = new Tag(null, NAME_3, null);
        List<Tag> tags = new ArrayList<>(Arrays.asList(tag1, tag2, tag3));

        doThrow(TagValidatorException.class).when(tagValidator).validateTagName(NAME);

        assertThrows(TagValidatorException.class, () -> tagServiceImpl.findOrCreateAll(tags));

        verify(tagValidator).validateTagName(anyString());
        verify(mockTagDao, never()).findAllExistingByNames(tags);
        verify(mockTagDao, never()).createAll(tags);
    }

    @Test
    void findByTagName() {
        Tag expected = new Tag(ID, NAME, null);

        when(mockTagDao.findByTagName(NAME)).thenReturn(Optional.of(expected));

        Optional<Tag> actual = tagServiceImpl.findByTagName(NAME);
        assertEquals(expected, actual.get());

        verify(tagValidator).validateTagName(NAME);
    }

    @Test
    void findByTagNameValidatorException() {
        doThrow(TagValidatorException.class).when(tagValidator).validateTagName(NAME);

        assertThrows(TagValidatorException.class,
                () -> tagServiceImpl.findByTagName(NAME));
    }

    @Test
    void findByTagNameNotFoundThenOptionalEmpty() {
        when(mockTagDao.findByTagName(NAME)).thenReturn(Optional.empty());

        assertEquals(Optional.empty(), tagServiceImpl.findByTagName(NAME));
    }

    @Test
    void findMostPopularTagOfUserHigherCostOrders() {
        Tag expected = new Tag(ID, NAME, null);

        when(mockTagDao.findMostPopularTagIdOfUserHigherCostOrders()).thenReturn(expected);

        Tag actual = tagServiceImpl.findMostPopularTagOfUserHigherCostOrders();
        assertEquals(expected, actual);
    }
}