package com.epam.mjc.core.repo;

import com.epam.mjc.api.entity.Tag;
import com.epam.mjc.api.repo.TagDao;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TagDaoImpl implements TagDao {

    private static final String FIND_ALL_SQL = "SELECT id,name FROM tag;";
    private static final String CREATE_SQL = "INSERT INTO tag (name) values (?);";
    private static final String DELETE_SQL = "DELETE FROM tag WHERE id = ?;";
    private static final String FIND_BY_ID_SQL = "SELECT id,name FROM tag WHERE id=?";

    private final JdbcTemplate template;
    private final RowMapper<Tag> rowMapper;

    private static final Logger log = LoggerFactory.getLogger(TagDaoImpl.class);

    @Override
    public List<Tag> findAll() {
        List<Tag> query = template.query(FIND_ALL_SQL, rowMapper);
        log.debug("findAll: query = {}", query);
        return query;
    }

    @Override
    public boolean create(Tag tag) {
        int update = template.update(CREATE_SQL, tag.getName());
        return update != 0;
    }

    @Override
    public boolean delete(Tag tag) {
        int update = template.update(DELETE_SQL, tag.getId());
        return update != 0;
    }

    @Override
    public Optional<Tag> findById(Long id) {
        return Optional.ofNullable(template.queryForObject(FIND_BY_ID_SQL, new Object[]{id}, rowMapper));
    }
}
