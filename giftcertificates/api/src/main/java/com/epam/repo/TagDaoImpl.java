package com.epam.repo;

import com.epam.entity.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TagDaoImpl implements TagDao {

    private static final String FIND_ALL_SQL = "SELECT id,name FROM tag;";
    private static final String CREATE_SQL = "INSERT INTO tag (name) values (?);";

    private final JdbcTemplate template;
    private final RowMapper<Tag> rowMapper;


    @Override
    public List<Tag> findAll() {
        return template.query(FIND_ALL_SQL, rowMapper);
    }

    @Override
    public boolean create(Tag tag) {
        int update = template.update(CREATE_SQL, tag.getName());
        return update != 0;
    }

    @Override
    public boolean delete(Tag tag) {
        int update = template.update("DELETE FROM tag WHERE id = ?;", tag.getId());
        return update != 0;
    }
}
