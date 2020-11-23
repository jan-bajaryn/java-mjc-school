package com.epam.mjc.core.util;

import com.epam.mjc.core.service.validator.PaginationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaginationCalculator {

    private final PaginationValidator paginationValidator;

    @Autowired
    public PaginationCalculator(PaginationValidator paginationValidator) {
        this.paginationValidator = paginationValidator;
    }

    public Integer calculateBegin(Integer pageNumber, Integer pageSize) {
        paginationValidator.validatePagination(pageNumber, pageSize);
        return (pageNumber - 1) * pageSize;
    }
}
