package com.epam.mjc.core.service.help;

import com.epam.mjc.api.dao.GiftCertificateDao;
import com.epam.mjc.api.domain.GiftCertificate;
import com.epam.mjc.api.service.exception.GiftCertificateAlreadyExists;
import com.epam.mjc.api.service.exception.GiftCertificateNameAlreadyExistsException;
import com.epam.mjc.api.service.exception.GiftCertificateNotFoundException;
import com.epam.mjc.api.service.exception.GiftCertificateValidatorException;
import com.epam.mjc.api.service.exception.PaginationException;
import com.epam.mjc.api.service.help.TagService;
import com.epam.mjc.api.service.validator.GiftCertificateValidator;
import com.epam.mjc.api.util.SearchParams;
import com.epam.mjc.core.util.PaginationCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceImplTest {

    private static final LocalDateTime NOW = LocalDateTime.now();
    private static final String NAME1 = "name";
    private static final String DESCRIPTION1 = "description";
    private static final int DURATION1 = 1;
    private static final BigDecimal PRICE1 = BigDecimal.valueOf(12.3);
    private static final long ID = 1L;
    private static final long SECOND_ID = 2L;

    @Mock
    private GiftCertificateDao giftCertificateDao;

    @Mock
    private TagService tagService;

    @Mock
    private GiftCertificateValidator giftCertificateValidator;

    @Mock
    private PaginationCalculator paginationCalculator;

    @InjectMocks
    private GiftCertificateServiceImpl giftCertificateService;

    private static final int PAGE_SIZE = 5;
    private static final int PAGE_NUMBER = 1;

    private GiftCertificate giftCertificate1;
    private GiftCertificate giftCertificate2;


    @BeforeEach
    void setUp() {
        giftCertificate1 = new GiftCertificate();
        giftCertificate1.setLastUpdateDate(NOW);
        giftCertificate1.setCreateDate(NOW);
        giftCertificate1.setName(NAME1);
        giftCertificate1.setDescription(DESCRIPTION1);
        giftCertificate1.setTags(new ArrayList<>());
        giftCertificate1.setDuration(DURATION1);
        giftCertificate1.setPrice(PRICE1);
        giftCertificate1.setPurchaseCertificates(new ArrayList<>());

        giftCertificate2 = new GiftCertificate();

        giftCertificate2.setId(ID);
        giftCertificate2.setLastUpdateDate(NOW);
        giftCertificate2.setCreateDate(NOW);
        giftCertificate2.setName(NAME1);
        giftCertificate2.setDescription(DESCRIPTION1);
        giftCertificate2.setTags(new ArrayList<>());
        giftCertificate2.setDuration(DURATION1);
        giftCertificate2.setPrice(PRICE1);
        giftCertificate2.setPurchaseCertificates(new ArrayList<>());

    }

    @Test
    void searchPaginationCalculatorThrowsExceptionThanThrowException() {
        when(paginationCalculator.calculateBegin(PAGE_NUMBER, PAGE_SIZE)).thenThrow(PaginationException.class);
        assertThrows(
                PaginationException.class,
                () -> giftCertificateService.search(new SearchParams(), PAGE_NUMBER, PAGE_SIZE)
        );
    }

    @Test
    void findById() {

        when(giftCertificateDao.findById(ID)).thenReturn(Optional.of(giftCertificate1));

        GiftCertificate actual = giftCertificateService.findById(ID);
        assertEquals(giftCertificate1, actual);

        verify(giftCertificateValidator).validateGiftCertificateId(ID);

    }

    @Test
    void findByIdValidatorException() {
        doThrow(GiftCertificateValidatorException.class)
                .when(giftCertificateValidator).validateGiftCertificateId(ID);

        assertThrows(GiftCertificateValidatorException.class,
                () -> giftCertificateService.findById(ID));
    }

    @Test
    void findByIdNotFoundException() {
        when(giftCertificateDao.findById(ID)).thenReturn(Optional.empty());

        assertThrows(GiftCertificateNotFoundException.class,
                () -> giftCertificateService.findById(ID));
    }

    @Test
    void create() {
        when(giftCertificateDao.findByName(NAME1)).thenReturn(Optional.empty());
        when(tagService.findOrCreateAll(any())).thenReturn(new ArrayList<>());
        when(giftCertificateDao.create(any(GiftCertificate.class))).thenReturn(giftCertificate1);

        GiftCertificate actual = giftCertificateService.create(giftCertificate1);
        assertSame(giftCertificate1, actual);

        verify(giftCertificateValidator, atLeastOnce()).validateGiftCertificate(any(GiftCertificate.class));
        verify(giftCertificateDao).findByName(NAME1);
        verify(giftCertificateDao).create(any(GiftCertificate.class));
    }

    @Test
    void createValidatorThrowsExceptionThanServiceThrowsException() {

        doThrow(GiftCertificateValidatorException.class).when(giftCertificateValidator).validateGiftCertificate(giftCertificate1);

        assertThrows(GiftCertificateValidatorException.class, () -> giftCertificateService.create(giftCertificate1));

        verify(giftCertificateValidator).validateGiftCertificate(giftCertificate1);
        verify(giftCertificateDao, never()).findByName(NAME1);
        verify(giftCertificateDao, never()).create(any(GiftCertificate.class));
    }


    @Test
    void createAlreadyExistsThanThrowsTagAlreadyExistsException() {

        when(giftCertificateDao.findByName(NAME1)).thenReturn(Optional.of(giftCertificate1));

        assertThrows(GiftCertificateAlreadyExists.class,
                () -> giftCertificateService.create(giftCertificate1));

        verify(giftCertificateValidator).validateGiftCertificate(giftCertificate1);
        verify(giftCertificateDao).findByName(NAME1);
        verify(giftCertificateDao, never()).create(any(GiftCertificate.class));
    }


    @Test
    void deleteById() {
        when(giftCertificateDao.findById(ID)).thenReturn(Optional.of(giftCertificate2));

        assertDoesNotThrow(() -> giftCertificateService.deleteById(ID));

        verify(giftCertificateValidator, atLeastOnce()).validateGiftCertificateId(ID);
        verify(giftCertificateDao).findById(ID);
        verify(giftCertificateDao).delete(giftCertificate2);
    }

    @Test
    void deleteByIdValidatorThrowsExceptionThantServiceThrowsException() {
        doThrow(GiftCertificateValidatorException.class)
                .when(giftCertificateValidator).validateGiftCertificateId(ID);

        assertThrows(GiftCertificateValidatorException.class, () -> giftCertificateService.deleteById(ID));

        verify(giftCertificateValidator).validateGiftCertificateId(ID);
        verify(giftCertificateDao, never()).findById(ID);
        verify(giftCertificateDao, never()).delete(any(GiftCertificate.class));
    }

    @Test
    void deleteByIdNotFoundThantTrowsTagNotFoundException() {

        when(giftCertificateDao.findById(ID)).thenReturn(Optional.empty());

        assertThrows(GiftCertificateNotFoundException.class,
                () -> giftCertificateService.deleteById(ID));

        verify(giftCertificateValidator, atLeastOnce()).validateGiftCertificateId(ID);
        verify(giftCertificateDao).findById(ID);
        verify(giftCertificateDao, never()).delete(any(GiftCertificate.class));
    }

    @Test
    void findByName() {

        when(giftCertificateDao.findByName(NAME1)).thenReturn(Optional.of(giftCertificate2));

        GiftCertificate actual = giftCertificateService.findByName(NAME1);
        assertEquals(giftCertificate2, actual);

        verify(giftCertificateValidator).validateGiftCertificateName(NAME1);
    }

    @Test
    void findByNameValidatorException() {
        doThrow(GiftCertificateValidatorException.class)
                .when(giftCertificateValidator).validateGiftCertificateName(NAME1);

        assertThrows(GiftCertificateValidatorException.class,
                () -> giftCertificateService.findByName(NAME1));
    }

    @Test
    void findByNameNotFoundThenNotFoundException() {
        when(giftCertificateDao.findByName(NAME1)).thenReturn(Optional.empty());

        assertThrows(GiftCertificateNotFoundException.class,
                () -> giftCertificateService.findByName(NAME1));
    }

    @Test
    void update() {
        when(giftCertificateDao.findByName(NAME1)).thenReturn(Optional.empty());
        when(giftCertificateDao.findById(ID)).thenReturn(Optional.of(giftCertificate2));
        when(tagService.findOrCreateAll(any())).thenReturn(new ArrayList<>());
        when(giftCertificateDao.update(any(GiftCertificate.class))).thenReturn(giftCertificate2);

        GiftCertificate actual = giftCertificateService.update(ID, giftCertificate2);
        assertSame(giftCertificate2, actual);

        verify(giftCertificateValidator, atLeastOnce()).validateGiftCertificate(any(GiftCertificate.class));
        verify(giftCertificateValidator, atLeastOnce()).validateGiftCertificateId(ID);
        verify(giftCertificateDao).findByName(NAME1);
        verify(giftCertificateDao).update(any(GiftCertificate.class));
    }

    @Test
    void updateValidatorThrowsExceptionThanServiceThrowsException() {

        doThrow(GiftCertificateValidatorException.class).when(giftCertificateValidator).validateGiftCertificate(giftCertificate1);

        assertThrows(GiftCertificateValidatorException.class, () -> giftCertificateService.update(ID, giftCertificate1));

        verify(giftCertificateValidator).validateGiftCertificate(giftCertificate1);
        verify(giftCertificateDao, never()).findByName(NAME1);
        verify(giftCertificateDao, never()).create(any(GiftCertificate.class));
    }


    @Test
    void updateAlreadyExistsThanThrowsTagAlreadyExistsException() {

        giftCertificate2.setId(SECOND_ID);

        when(giftCertificateDao.findByName(NAME1)).thenReturn(Optional.of(giftCertificate2));

        assertThrows(GiftCertificateNameAlreadyExistsException.class,
                () -> giftCertificateService.update(ID, giftCertificate1));

        verify(giftCertificateValidator).validateGiftCertificate(giftCertificate1);
        verify(giftCertificateDao).findByName(NAME1);
        verify(giftCertificateDao, never()).update(any(GiftCertificate.class));
    }

}