package com.epam.mjc.core.service.help;

import com.epam.mjc.api.dao.OrderDao;
import com.epam.mjc.api.domain.GiftCertificate;
import com.epam.mjc.api.domain.Order;
import com.epam.mjc.api.domain.PurchaseCertificate;
import com.epam.mjc.api.domain.User;
import com.epam.mjc.api.model.CertificateRequestModel;
import com.epam.mjc.api.model.OrderForCreate;
import com.epam.mjc.api.service.exception.EmptyGiftCertificates;
import com.epam.mjc.api.service.exception.OrderNotFountException;
import com.epam.mjc.api.service.exception.OrderValidatorException;
import com.epam.mjc.api.service.exception.PaginationException;
import com.epam.mjc.api.service.help.GiftCertificateService;
import com.epam.mjc.api.service.help.UserService;
import com.epam.mjc.core.service.validator.CountValidator;
import com.epam.mjc.core.service.validator.OrderValidator;
import com.epam.mjc.core.service.validator.UserValidator;
import com.epam.mjc.core.util.PaginationCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {


    private static final int PAGE_SIZE = 5;
    private static final int PAGE_NUMBER = 1;
    private static final Long ID = 1L;
    private static final long USER_ID = 1L;
    private static final int COUNT = 1;
    private static final long MODEL_ID = 1L;
    private static final int PRICE = 1;

    @Mock
    private OrderDao orderDao;

    @Mock
    private UserService userService;

    @Mock
    private OrderValidator orderValidator;

    @Mock
    private UserValidator userValidator;

    @Mock
    private GiftCertificateService giftCertificateService;

    @Mock
    private PaginationCalculator paginationCalculator;

    @Mock
    private CountValidator countValidator;

    @Mock
    private PurchaseCertificateService purchaseCertificateService;

    @InjectMocks
    private OrderServiceImpl orderService;

    private OrderForCreate orderForCreate;
    private CertificateRequestModel model1;
    private GiftCertificate giftCertificate1;
    private User user;
    private Order order;
    private PurchaseCertificate purchaseCertificate;


    @BeforeEach
    void setUp() {
        orderForCreate = new OrderForCreate();
        orderForCreate.setUserId(USER_ID);

        List<CertificateRequestModel> certificates = new ArrayList<>();

        model1 = new CertificateRequestModel();
        model1.setCount(COUNT);
        model1.setId(MODEL_ID);
        certificates.add(model1);

        orderForCreate.setGiftCertificates(certificates);

        giftCertificate1 = new GiftCertificate();
        giftCertificate1.setPrice(BigDecimal.valueOf(PRICE));

        user = new User();

        order = new Order();
        purchaseCertificate = new PurchaseCertificate();

    }

    @Test
    void searchPaginationCalculatorThrowsExceptionThanThrowException() {
        when(paginationCalculator.calculateBegin(PAGE_NUMBER, PAGE_SIZE)).thenThrow(PaginationException.class);
        assertThrows(
                PaginationException.class,
                () -> orderService.search(ID, PAGE_NUMBER, PAGE_SIZE)
        );
    }

    @Test
    void searchValidatorThrowsExceptionThanThrowException() {
        doThrow(OrderValidatorException.class).when(userValidator).validateIdNullable(ID);
        assertThrows(
                OrderValidatorException.class,
                () -> orderService.search(ID, PAGE_NUMBER, PAGE_SIZE)
        );
    }

    @Test
    void findById() {
        Order order = new Order();
        order.setId(ID);

        when(orderDao.findById(ID)).thenReturn(Optional.of(order));

        Order actual = orderService.findById(ID);
        assertEquals(order, actual);

        verify(orderValidator).validateId(ID);

    }

    @Test
    void findByIdValidatorException() {
        doThrow(OrderValidatorException.class)
                .when(orderValidator).validateId(ID);

        assertThrows(OrderValidatorException.class,
                () -> orderService.findById(ID));
    }

    @Test
    void findByIdNotFoundException() {
        when(orderDao.findById(ID)).thenReturn(Optional.empty());

        assertThrows(OrderNotFountException.class,
                () -> orderService.findById(ID));
    }

    @Test
    void create() {
        Order expected = new Order();

        when(giftCertificateService.findById(MODEL_ID)).thenReturn(giftCertificate1);
        when(userService.findById(orderForCreate.getUserId())).thenReturn(user);
        when(orderDao.create(any(Order.class))).thenReturn(expected);
        when(purchaseCertificateService.create(any(PurchaseCertificate.class))).thenReturn(purchaseCertificate);

        Order actual = orderService.create(orderForCreate);

        assertEquals(expected, actual);

        verify(giftCertificateService, atLeastOnce()).findById(MODEL_ID);
        verify(userService, atLeastOnce()).findById(orderForCreate.getUserId());
        verify(orderDao).create(any(Order.class));
        verify(purchaseCertificateService, atLeastOnce()).create(any(PurchaseCertificate.class));
    }

    @Test
    void createEmptyGiftCertificatesExceptionThantThrowException() {

        orderForCreate.setGiftCertificates(new ArrayList<>());

        assertThrows(EmptyGiftCertificates.class, () -> orderService.create(orderForCreate));

        verify(giftCertificateService, never()).findById(any(Long.class));
        verify(userService, never()).findById(any(Long.class));
        verify(orderDao,never()).create(any(Order.class));
        verify(purchaseCertificateService, never()).create(any(PurchaseCertificate.class));
    }

    

}