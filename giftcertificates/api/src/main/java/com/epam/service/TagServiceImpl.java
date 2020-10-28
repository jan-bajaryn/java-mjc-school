package com.epam.service;

import com.epam.entity.Tag;
import com.epam.repo.TagDaoImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagDaoImpl tagDaoImpl;

    public List<Tag> findAll() {
        return tagDaoImpl.findAll();
    }

}
