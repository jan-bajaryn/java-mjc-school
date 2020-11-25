package com.epam.mjc.core.service.help;

import com.epam.mjc.api.dao.OrderDao;
import com.epam.mjc.api.domain.GiftCertificate;
import com.epam.mjc.api.domain.Order;
import com.epam.mjc.api.domain.PurchaseCertificate;
import com.epam.mjc.api.model.CertificateRequestModel;
import com.epam.mjc.api.model.OrderForCreate;
import com.epam.mjc.api.service.exception.EmptyGiftCertificates;
import com.epam.mjc.api.service.exception.MissedIdAndNameException;
import com.epam.mjc.api.service.exception.OrderNotFountException;
import com.epam.mjc.api.service.help.GiftCertificateService;
import com.epam.mjc.api.service.help.OrderService;
import com.epam.mjc.api.service.help.UserService;
import com.epam.mjc.core.service.validator.CountValidator;
import com.epam.mjc.core.service.validator.OrderValidator;
import com.epam.mjc.core.service.validator.UserValidator;
import com.epam.mjc.core.util.PaginationCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PrePersist;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderDao orderDao;
    private final UserService userService;
    private final OrderValidator orderValidator;
    private final UserValidator userValidator;
    private final GiftCertificateService giftCertificateService;
    private final PaginationCalculator paginationCalculator;
    private final CountValidator countValidator;
    private final PurchaseCertificateService purchaseCertificateService;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao, UserService userService, OrderValidator orderValidator, UserValidator userValidator, GiftCertificateService giftCertificateService, PaginationCalculator paginationCalculator, CountValidator countValidator, PurchaseCertificateService purchaseCertificateService) {
        this.orderDao = orderDao;
        this.userService = userService;
        this.orderValidator = orderValidator;
        this.userValidator = userValidator;
        this.giftCertificateService = giftCertificateService;
        this.paginationCalculator = paginationCalculator;
        this.countValidator = countValidator;
        this.purchaseCertificateService = purchaseCertificateService;
    }

    @Override
    public List<Order> search(Long userId, Integer pageNumber, Integer pageSize) {
        userValidator.validateIdNullable(userId);
        return orderDao.search(userId, paginationCalculator.calculateBegin(pageNumber, pageSize), pageSize);
    }

    @Override
    public Order findById(Long id) {
        orderValidator.validateId(id);
        return orderDao.findById(id).orElseThrow(() -> new OrderNotFountException("order.not-fount", id));
    }

    @Override
    @PrePersist
    @Transactional
    public Order create(OrderForCreate orderForCreate) {

        log.debug("create: orderForCreate = {}", orderForCreate);
        checkEmptyGiftCertificates(orderForCreate);

        Map<GiftCertificate, Integer> giftCertificates =
                buildGiftCertificates(orderForCreate.getGiftCertificates());

        Order order = new Order();
        order.setCreateDate(LocalDateTime.now());

        order.setUser(userService.findById(orderForCreate.getUserId()));
        log.debug("giftCertificates = {}", giftCertificates);
        order.setPrice(calculatePrice(giftCertificates));
        log.debug("before create");

        Order result = orderDao.create(order);
        result.setPurchaseCertificates(buildPurchases(giftCertificates, order));
        return result;
    }

    private List<PurchaseCertificate> buildPurchases(Map<GiftCertificate, Integer> buildGiftCertificates, final Order order) {
        return buildGiftCertificates.entrySet().stream()
                .map(e -> {
                    PurchaseCertificate pc = new PurchaseCertificate();
                    pc.setCount(e.getValue());
                    pc.setGiftCertificate(e.getKey());
                    log.debug("buildPurchases: order = {}", order);
                    pc.setOrder(order);
                    pc.setOldName(e.getKey().getName());
                    pc.setPriceForOne(e.getKey().getPrice());
                    return purchaseCertificateService.create(pc);
                })
                .collect(Collectors.toList());
    }

    private void checkEmptyGiftCertificates(OrderForCreate orderForCreate) {
        if (orderForCreate.getGiftCertificates() == null
                || orderForCreate.getGiftCertificates().isEmpty()) {
            throw new EmptyGiftCertificates("order.empty-gift-certificates");
        }
    }

    private BigDecimal calculatePrice(Map<GiftCertificate, Integer> giftCertificates) {
        BigDecimal result = BigDecimal.ZERO;
        for (Map.Entry<GiftCertificate, Integer> entry : giftCertificates.entrySet()) {
            result = result.add(
                    entry.getKey().getPrice().multiply(
                            BigDecimal.valueOf(entry.getValue())
                    )
            );
        }
        return result;
    }

    private Map<GiftCertificate, Integer> buildGiftCertificates(List<CertificateRequestModel> giftCertificatesModels) {
        return giftCertificatesModels.stream()
                .collect(Collectors.toMap(this::findByIdOrName,
                        CertificateRequestModel::getCount));
    }

    private GiftCertificate findByIdOrName(CertificateRequestModel model) {
        countValidator.validateCount(model.getCount());
        if (model.getId() != null) {
            return giftCertificateService.findById(model.getId());
        }
        if (model.getName() != null) {
            return giftCertificateService.findByName(model.getName());
        }
        throw new MissedIdAndNameException("order.missed-id-name");
    }
}
