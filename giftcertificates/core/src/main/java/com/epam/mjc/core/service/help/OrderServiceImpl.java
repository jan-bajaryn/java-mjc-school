package com.epam.mjc.core.service.help;

import com.epam.mjc.api.dao.OrderDao;
import com.epam.mjc.api.domain.GiftCertificate;
import com.epam.mjc.api.domain.Order;
import com.epam.mjc.api.model.OrderForCreate;
import com.epam.mjc.api.service.exception.EmptyGiftCertificates;
import com.epam.mjc.api.service.exception.OrderNotFountException;
import com.epam.mjc.api.service.help.GiftCertificateService;
import com.epam.mjc.api.service.help.OrderService;
import com.epam.mjc.api.service.help.UserService;
import com.epam.mjc.core.service.validator.OrderValidator;
import com.epam.mjc.core.service.validator.UserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PrePersist;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderDao orderDao;
    private final UserService userService;
    private final OrderValidator orderValidator;
    private final UserValidator userValidator;
    private final GiftCertificateService giftCertificateService;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao, UserService userService, OrderValidator orderValidator, UserValidator userValidator, GiftCertificateService giftCertificateService) {
        this.orderDao = orderDao;
        this.userService = userService;
        this.orderValidator = orderValidator;
        this.userValidator = userValidator;
        this.giftCertificateService = giftCertificateService;
    }

    @Override
    public List<Order> search(Long userId) {
        userValidator.validateIdNullable(userId);
        return orderDao.search(userId);
    }

    @Override
    public Order findById(Long id) {
        orderValidator.validateId(id);
        return orderDao.findById(id).orElseThrow(() -> new OrderNotFountException("order.not-fount"));
    }

    @Override
    @PrePersist
    @Transactional
    public Order create(OrderForCreate orderForCreate) {

        log.debug("create: orderForCreate = {}", orderForCreate);
        checkEmptyGiftCertificates(orderForCreate);

        Order order = new Order();
        order.setCreateDate(LocalDateTime.now());
        order.setGiftCertificates(
                buildGiftCertificates(orderForCreate.getGiftCertificatesIds())
        );
        order.setUser(userService.findById(orderForCreate.getUserId()));
        order.setPrice(calculatePrice(order));
        log.debug("before create");
        return orderDao.create(order);
    }

    private void checkEmptyGiftCertificates(OrderForCreate orderForCreate) throws EmptyGiftCertificates {
        if (orderForCreate.getGiftCertificatesIds() == null
                || orderForCreate.getGiftCertificatesIds().isEmpty()) {
            throw new EmptyGiftCertificates("order.empty-gift-certificates");
        }
    }

    private BigDecimal calculatePrice(Order order) {
        BigDecimal result = BigDecimal.ZERO;
        for (GiftCertificate giftCertificate : order.getGiftCertificates()) {
            result = result.add(giftCertificate.getPrice());
        }
        return result;
    }

    private List<GiftCertificate> buildGiftCertificates(List<Long> giftCertificatesIds) {
        return giftCertificatesIds.stream()
                .map(giftCertificateService::findById)
                .collect(Collectors.toList());
    }
}
