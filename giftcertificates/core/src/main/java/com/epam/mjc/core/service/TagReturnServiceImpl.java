package com.epam.mjc.core.service;

import com.epam.mjc.api.model.dto.TagDto;
import com.epam.mjc.api.service.TagReturnService;
import com.epam.mjc.api.service.help.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.epam.mjc.api.service.mapper.TagDtoMapper;

import java.util.List;

@Service
public class TagReturnServiceImpl implements TagReturnService {

    private final TagService service;
    private final TagDtoMapper tagDtoMapper;

    @Autowired
    public TagReturnServiceImpl(TagService service, TagDtoMapper tagDtoMapper) {
        this.service = service;
        this.tagDtoMapper = tagDtoMapper;
    }

    @Override
    public TagDto createByName(String name) {
        return tagDtoMapper.toTagDto(service.createByName(name));
    }

    @Override
    public boolean deleteById(Long id) {
        return service.deleteById(id);
    }

    @Override
    public List<TagDto> findAll(Integer pageNumber,Integer pageSize) {
        return tagDtoMapper.toTagDto(service.findAll(pageNumber,pageSize));
    }

    @Override
    public TagDto findById(Long id) {
        return tagDtoMapper.toTagDto(service.findById(id));
    }

    @Override
    public TagDto findMostPopularTagOfUserHigherCostOrders() {
        return tagDtoMapper.toTagDto(service.findMostPopularTagOfUserHigherCostOrders());
    }
}
