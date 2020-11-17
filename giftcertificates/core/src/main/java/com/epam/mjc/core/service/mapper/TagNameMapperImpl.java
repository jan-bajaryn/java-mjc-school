package com.epam.mjc.core.service.mapper;

import com.epam.mjc.api.service.exception.TagNameMapperException;
import com.epam.mjc.api.service.mapper.TagNameMapper;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TagNameMapperImpl implements TagNameMapper {

    private static final String COMMA = ",";

    @Override
    public List<String> toTagNameList(String tagName) {
        if (tagName == null) {
            return null;
        }
        return Arrays.stream(tagName.split(COMMA))
                .peek(s -> {
                    if (s.isEmpty()) {
                        throw new TagNameMapperException("tag.mapper-list");
                    }
                })
                .collect(Collectors.toList());
    }
}
