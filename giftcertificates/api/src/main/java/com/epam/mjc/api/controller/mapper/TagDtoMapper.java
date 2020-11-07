package com.epam.mjc.api.controller.mapper;

import com.epam.mjc.api.domain.Tag;
import com.epam.mjc.api.model.TagModel;
import com.epam.mjc.api.model.dto.TagDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TagDtoMapper {
    TagDtoMapper INSTANCE = Mappers.getMapper(TagDtoMapper.class);

    TagDto toTagDto(Tag tag);

    List<TagDto> toTagDto(List<Tag> tags);

    @Mapping(target = "id", ignore = true)
    Tag toTag(TagModel tagModel);

    List<Tag> toTag(List<TagModel> tagModels);
}
