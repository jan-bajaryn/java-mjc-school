package com.epam.mjc.core.repo;

import com.epam.mjc.api.entity.Tag;
import com.epam.mjc.api.repo.TagDao;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TagDaoImpl implements TagDao {

    private static final String FIND_ALL_SQL = "SELECT id,name FROM tag;";


    private static final String CREATE_SQL = "INSERT INTO tag (name) values (?);";
    private static final String DELETE_SQL = "DELETE FROM tag WHERE id = ?;";
    private static final String FIND_BY_ID_SQL = "SELECT id,name FROM tag WHERE id=?";
    private static final String ID = "id";
    private static final String FIND_BY_CERTIFICATE_ID = "SELECT tag.id, tag.name FROM tag INNER JOIN gift_certificate_tag ON tag.id = gift_certificate_tag.tag_id WHERE gift_certificate_id = ?;";

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
    public Tag create(Tag tag) {

        log.debug("create: method entered");
        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            log.debug("try entered");
            template.update(connection -> {
                PreparedStatement ps = connection
                        .prepareStatement(CREATE_SQL, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, tag.getName());
                return ps;
            }, keyHolder);
        } catch (DataAccessException e) {
            log.error("message: ", e);
        }
        tag.setId((Long) Objects.requireNonNull(keyHolder.getKeys().get(ID)));
        return tag;

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

    @Override
    public List<Tag> findAllByGiftCertificateId(Long id) {
        return template.query(FIND_BY_CERTIFICATE_ID, new Object[]{id}, rowMapper);
    }
}
