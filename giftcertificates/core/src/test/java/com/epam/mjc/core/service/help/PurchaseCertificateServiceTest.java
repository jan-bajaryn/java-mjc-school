package com.epam.mjc.core.service.help;

import com.epam.mjc.api.domain.PurchaseCertificate;
import com.epam.mjc.api.service.exception.PurchaseCertificateValidatorException;
import com.epam.mjc.core.dao.PurchaseCertificateDao;
import com.epam.mjc.core.service.validator.PurchaseCertificateValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PurchaseCertificateServiceTest {

    @Mock
    private PurchaseCertificateDao purchaseCertificateDao;
    @Mock
    private PurchaseCertificateValidator validator;

    @InjectMocks
    private PurchaseCertificateService purchaseCertificateService;

    @Test
    void create() {
        PurchaseCertificate expected = new PurchaseCertificate();

        when(purchaseCertificateDao.create(expected)).thenReturn(expected);

        PurchaseCertificate actual = purchaseCertificateService.create(expected);

        assertEquals(expected, actual);

        verify(validator).validatePurchaseCertificate(expected);
        verify(purchaseCertificateDao).create(expected);
    }

    @Test
    void createValidatorExceptionThanThrowsValidatorException() {
        PurchaseCertificate purchaseCertificate = new PurchaseCertificate();

        doThrow(PurchaseCertificateValidatorException.class)
                .when(validator).validatePurchaseCertificate(purchaseCertificate);

        assertThrows(PurchaseCertificateValidatorException.class,
                () -> purchaseCertificateService.create(purchaseCertificate));

        verify(validator).validatePurchaseCertificate(purchaseCertificate);
        verify(purchaseCertificateDao, never()).create(purchaseCertificate);
    }
}