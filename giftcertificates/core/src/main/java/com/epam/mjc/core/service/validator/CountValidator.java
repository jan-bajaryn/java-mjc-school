package com.epam.mjc.core.service.validator;

import com.epam.mjc.api.service.exception.CountValidatorException;
import org.springframework.stereotype.Component;

@Component
public class CountValidator {
    public void validateCount(Integer count) {
        if (count == null || count<1){
            throw new CountValidatorException("order.wrong-count");
        }
    }
}
