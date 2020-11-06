package com.epam.mjc.api.controller.mapper;

import com.epam.mjc.api.domain.Tag;
import com.epam.mjc.api.model.TagModel;

import java.util.List;

public interface TagMapper {

    Tag toTag(TagModel tagModel);

    List<Tag> toTag(List<TagModel> tagModels);
    
}
