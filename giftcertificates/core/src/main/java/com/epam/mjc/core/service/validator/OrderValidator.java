package com.epam.mjc.core.service.validator;

import com.epam.mjc.api.service.exception.OrderValidatorException;
import org.springframework.stereotype.Component;

@Component
public class OrderValidator {

    public void validateId(Long id) {
        if (id == null || id < 1) {
            throw new OrderValidatorException("order.wrong-id");
        }
    }
}
