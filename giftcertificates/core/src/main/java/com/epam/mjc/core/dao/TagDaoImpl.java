package com.epam.mjc.core.dao;

import com.epam.mjc.api.dao.TagDao;
import com.epam.mjc.api.domain.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class TagDaoImpl implements TagDao {
    private static final String FIND_ALL_SQL = "SELECT id,name FROM tag;";
    private static final String CREATE_SQL = "INSERT INTO tag (name) values (?);";
    private static final String DELETE_SQL = "DELETE FROM tag WHERE id = ?;";
    private static final String FIND_BY_ID_SQL = "SELECT id,name FROM tag WHERE id=?";
    private static final String ID = "id";
    private static final String FIND_BY_CERTIFICATE_ID = "SELECT tag.id, tag.name FROM tag INNER JOIN gift_certificate_tag ON tag.id = gift_certificate_tag.tag_id WHERE gift_certificate_id = ?;";
    private static final String FIND_BY_TAG_NAME = "SELECT id, name FROM tag WHERE name =?;";
    private static final String FIND_EXISTING_SQL = "SELECT id,name FROM tag WHERE name IN (%s)";
    private static final String COMMA = ",";
    private static final String QUESTION_MARK = "?";
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
                PreparedStatement ps = connection.prepareStatement(CREATE_SQL, Statement.RETURN_GENERATED_KEYS);
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
        try {
            Tag tag = template.queryForObject(FIND_BY_ID_SQL, new Object[]{id}, rowMapper);
            return Optional.ofNullable(tag);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }

    }

    @Override
    public List<Tag> findAllByGiftCertificateId(Long id) {
        return template.query(FIND_BY_CERTIFICATE_ID, new Object[]{id}, rowMapper);
    }

    @Override
    public Optional<Tag> findByTagName(String name) {
        try {
            Tag tag = template.queryForObject(FIND_BY_TAG_NAME, new Object[]{name}, rowMapper);
            log.debug("findByTagName: tag = {}", tag);
            return Optional.ofNullable(tag);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Tag> findAllExistingByNames(List<Tag> tags) {
        String[] names = tags.stream()
                .map(Tag::getName)
                .toArray(String[]::new);
        String inSql = String.join(COMMA, Collections.nCopies(names.length, QUESTION_MARK));

        return template.query(
                String.format(FIND_EXISTING_SQL, inSql),
                names,
                rowMapper);
    }

    @Override
    public void createAll(List<Tag> toAdd) {
//        int[] updateCounts = template.batchUpdate(
//                CREATE_SQL,
//                new BatchPreparedStatementSetter() {
//                    public void setValues(PreparedStatement ps, int i) throws SQLException {
//                        ps.setString(1, toAdd.get(i).getName());
//                    }
//
//                    public int getBatchSize() {
//                        return toAdd.size();
//                    }
//                });
    }

    public TagDaoImpl(final JdbcTemplate template, final RowMapper<Tag> rowMapper) {
        this.template = template;
        this.rowMapper = rowMapper;
    }
}
