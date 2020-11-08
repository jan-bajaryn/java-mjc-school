package com.epam.mjc.api.service.mapper;

import com.epam.mjc.api.util.sort.SortParams;

public interface SortMapper {

    SortParams toSortParams(String sort);
}
