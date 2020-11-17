package com.epam.mjc.core.service.help;

import com.epam.mjc.api.dao.OrderDao;
import com.epam.mjc.api.domain.GiftCertificate;
import com.epam.mjc.api.domain.Order;
import com.epam.mjc.api.model.OrderForCreate;
import com.epam.mjc.api.service.exception.OrderNotFountException;
import com.epam.mjc.api.service.help.OrderService;
import com.epam.mjc.api.service.help.UserService;
import com.epam.mjc.core.service.validator.OrderValidator;
import com.epam.mjc.core.service.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;
    private final UserService userService;
    private final OrderValidator orderValidator;
    private final UserValidator userValidator;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao, UserService userService, OrderValidator orderValidator, UserValidator userValidator) {
        this.orderDao = orderDao;
        this.userService = userService;
        this.orderValidator = orderValidator;
        this.userValidator = userValidator;
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
// TODO complete creation
    @Override
    public Order create(OrderForCreate orderForCreate) {
        Order order = new Order();
        order.setCreateDate(LocalDateTime.now());
        order.setGiftCertificates(
                buildGiftCertificates(orderForCreate.getGiftCertificatesIds())
        );
        order.setUser(userService.findById(orderForCreate.getUserId()));
        order.setPrice(calculatePrice(order));
        return orderDao.create(order);
    }

    private BigDecimal calculatePrice(Order order) {
        return null;
    }

    private List<GiftCertificate> buildGiftCertificates(List<Long> giftCertificatesIds) {
        return null;
    }
}
