package com.epam.repo;

import com.epam.entity.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TagDao {

    private static final String FIND_ALL = "SELECT id,name FROM tag;";

    private final JdbcTemplate template;
    private final RowMapper<Tag> rowMapper;


    public List<Tag> findAll() {
        return template.query(FIND_ALL, rowMapper);
    }
}
