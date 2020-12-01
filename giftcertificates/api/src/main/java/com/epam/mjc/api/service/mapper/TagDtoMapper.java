package com.epam.mjc.api.service.mapper;

import com.epam.mjc.api.controller.TagController;
import com.epam.mjc.api.domain.Tag;
import com.epam.mjc.api.model.TagModel;
import com.epam.mjc.api.model.dto.TagDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.hateoas.Link;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Mapper(componentModel = "spring")
public interface TagDtoMapper {
    TagDtoMapper INSTANCE = Mappers.getMapper(TagDtoMapper.class);

    TagDto toTagDto(Tag tag);

    @AfterMapping
    default void afterToTagDto(Tag from, @MappingTarget TagDto to) {
        setSelfLinks(to);
    }

    default void setSelfLinks(TagDto byId) {
        Link selfLink = linkTo(methodOn(TagController.class)
                .showById(byId.getId())).withSelfRel();
        byId.add(selfLink);
        Link delete = linkTo(methodOn(TagController.class)
                .tagDelete(byId.getId())).withRel("delete");
        byId.add(delete);
    }

    List<TagDto> toTagDto(List<Tag> tags);

    @Mapping(target = "id", ignore = true)
    Tag toTag(TagModel tagModel);

    List<Tag> toTag(List<TagModel> tagModels);
}
