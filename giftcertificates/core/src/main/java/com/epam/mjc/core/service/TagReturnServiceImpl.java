package com.epam.mjc.core.service;

import com.epam.mjc.api.domain.Role;
import com.epam.mjc.api.domain.User;
import com.epam.mjc.api.model.dto.TagDto;
import com.epam.mjc.api.service.TagReturnService;
import com.epam.mjc.api.service.help.TagService;
import com.epam.mjc.api.util.HateoasManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.epam.mjc.api.service.mapper.TagDtoMapper;

import java.util.List;

@Service
public class TagReturnServiceImpl implements TagReturnService {

    private final TagService service;
    private final TagDtoMapper tagDtoMapper;
    private final HateoasManager hateoasManager;


    @Autowired
    public TagReturnServiceImpl(TagService service, TagDtoMapper tagDtoMapper, HateoasManager hateoasManager) {
        this.service = service;
        this.tagDtoMapper = tagDtoMapper;
        this.hateoasManager = hateoasManager;
    }

    @Override
    public TagDto createByName(String name) {
        TagDto tagDto = tagDtoMapper.toTagDto(service.createByName(name));
        hateoasManager.setSelfLinksAdmin(tagDto);
        return tagDto;
    }

    @Override
    public void deleteById(Long id) {
        service.deleteById(id);
    }

    @Override
    public CollectionModel<TagDto> findAll(Integer pageNumber, Integer pageSize) {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<TagDto> all = tagDtoMapper.toTagDto(service.findAll(pageNumber, pageSize));
        CollectionModel<TagDto> model = new CollectionModel<>(all);

        if (principal != null && principal.getRole() == Role.ADMIN) {
            hateoasManager.collectionLinksAdmin(model);
        } else {
            hateoasManager.tagCollectionLinksNotAdmin(model);
        }
        return model;
    }

    @Override
    public TagDto findById(Long id) {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        TagDto tagDto = tagDtoMapper.toTagDto(service.findById(id));

        if (principal != null && principal.getRole() == Role.ADMIN) {
            hateoasManager.setSelfLinksAdmin(tagDto);
        } else {
            hateoasManager.selfLinksNotAdmin(tagDto);
        }
        return tagDto;
    }

    @Override
    public TagDto findMostPopularTagOfUserHigherCostOrders() {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TagDto result = tagDtoMapper.toTagDto(service.findMostPopularTagOfUserHigherCostOrders());
        if (principal != null && principal.getRole() == Role.ADMIN) {
            hateoasManager.setSelfLinksAdmin(result);
        } else {
            hateoasManager.selfLinksNotAdmin(result);
        }
        return result;
    }
}
