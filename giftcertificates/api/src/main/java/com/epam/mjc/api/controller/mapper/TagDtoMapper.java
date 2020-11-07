package com.epam.mjc.api.controller.mapper;

import com.epam.mjc.api.domain.Tag;
import com.epam.mjc.api.model.dto.TagDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TagDtoMapper {
    TagMapper INSTANCE = Mappers.getMapper(TagMapper.class);

    TagDto toTagDto(Tag tag);

    List<TagDto> toTagDto(List<Tag> tags);
}
