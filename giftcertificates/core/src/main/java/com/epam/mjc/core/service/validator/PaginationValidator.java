package com.epam.mjc.core.service.validator;

import com.epam.mjc.api.service.exception.PaginationException;
import org.springframework.stereotype.Component;

@Component
public class PaginationValidator {
    public void validatePagination(Integer pageNumber, Integer pageSize) {
        if (pageNumber == null || pageSize == null || pageNumber < 1 || pageSize < 1) {
            throw new PaginationException("pagination.wrong-parameters");
        }
    }
}
