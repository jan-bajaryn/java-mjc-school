package com.epam.mjc.core.controller.mapper;

import com.epam.mjc.api.domain.Tag;
import com.epam.mjc.api.model.TagModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagMapper {
    Tag toTag(TagModel tagModel) {
        return Tag.builder().name(tagModel.getName()).build();
    }

    List<Tag> toTag(List<TagModel> tagModels) {
        return tagModels.stream()
                .map(this::toTag)
                .collect(Collectors.toList());
    }
}
