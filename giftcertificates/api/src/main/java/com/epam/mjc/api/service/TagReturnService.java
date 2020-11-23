package com.epam.mjc.api.service;

import com.epam.mjc.api.model.dto.TagDto;

import java.util.List;

public interface TagReturnService {
    TagDto createByName(String name);

    boolean deleteById(Long id);

    List<TagDto> findAll(Integer pageNumber, Integer pageSize);

    TagDto findById(Long id);

    TagDto findMostPopularTagOfUserHigherCostOrders();
}
