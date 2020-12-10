package com.epam.mjc.api.service;

import com.epam.mjc.api.model.dto.TagDto;
import org.springframework.hateoas.CollectionModel;

public interface TagReturnService {
    TagDto createByName(String name);

    void deleteById(Long id);

    CollectionModel<TagDto> findAll(Integer pageNumber, Integer pageSize);

    TagDto findById(Long id);

    TagDto findMostPopularTagOfUserHigherCostOrders();
}
