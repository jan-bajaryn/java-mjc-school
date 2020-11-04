package com.epam.mjc.core.repo;

import com.epam.mjc.api.entity.GiftCertificate;
import com.epam.mjc.api.entity.Tag;
import com.epam.mjc.api.repo.GiftCertificateDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {

    private static final String FIND_BY_ID_SQL = "SELECT id,description,price,createDate,lastUpdateDate,duration FROM gift_certificate WHERE id = ?;";
    private static final String FIND_ALL_SQL = "SELECT id,description,price,createDate,lastUpdateDate,duration FROM gift_certificate;";
    private static final String DELETE_SQL = "DELETE FROM gift_certificate WHERE id=?;";
    private static final String FIND_ALL_BY_TAG_NAME = "SELECT gift_certificate.id,description,price,createDate,lastUpdateDate,duration FROM gift_certificate INNER JOIN gift_certificate_tag ON gift_certificate.id = gift_certificate_tag.gift_certificate_id INNER JOIN tag ON gift_certificate_tag.tag_id = tag.id WHERE tag.name = ?;";
    private static final String FIND_ALL_BY_PART_NAME_AND_PART_DESCRIPTION = "SELECT id,description,price,createDate,lastUpdateDate,duration FROM gift_certificate WHERE name LIKE '%\\?%' AND description LIKE '%\\?%';";
    private static final String CREATE_SQL = "INSERT INTO gift_certificate (name, description, price, createDate, lastUpdateDate, duration) VALUES (?,?,?,?,?,?);";
    private static final String UPDATE_SQL = "UPDATE gift_certificate SET  name = ?, description = ?, price = ?, createDate = ?, lastUpdateDate = ?, duration = ? WHERE id = ?;";

    private static final Logger log = LoggerFactory.getLogger(GiftCertificateDaoImpl.class);
    private static final String ID = "id";

    private final JdbcTemplate template;
    private final RowMapper<GiftCertificate> rowMapper;

    @Autowired
    public GiftCertificateDaoImpl(JdbcTemplate template, RowMapper<GiftCertificate> rowMapper) {
        this.template = template;
        this.rowMapper = rowMapper;
    }

    @Override
    public GiftCertificate create(GiftCertificate giftCertificate) {

        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            template.update(connection -> {
                PreparedStatement ps = connection
                        .prepareStatement(CREATE_SQL, Statement.RETURN_GENERATED_KEYS);

                LocalDateTime now = LocalDateTime.now();

                ps.setString(1, giftCertificate.getName());
                ps.setString(2, giftCertificate.getDescription());
                ps.setBigDecimal(3, giftCertificate.getPrice());
                ps.setTimestamp(4, Timestamp.valueOf(now));
                ps.setTimestamp(5, Timestamp.valueOf(now));
                ps.setInt(6, giftCertificate.getDuration());
                return ps;
            }, keyHolder);
        } catch (DataAccessException e) {
            log.error("message: ", e);
        }
        giftCertificate.setId((Long) Objects.requireNonNull(keyHolder.getKeys().get(ID)));
        return giftCertificate;

    }

    @Override
    public boolean update(GiftCertificate giftCertificate) {
        int update = template.update(
                UPDATE_SQL,
                giftCertificate.getName(),
                giftCertificate.getDescription(),
                giftCertificate.getPrice(),
                giftCertificate.getCreateDate(),
                giftCertificate.getLastUpdateDate(),
                giftCertificate.getDuration(),
                giftCertificate.getId()
        );
        return update != 0;
    }

    @Override
    public GiftCertificate findById(Long id) {
        return template.queryForObject(FIND_BY_ID_SQL, new Object[]{id}, rowMapper);
    }

    @Override
    public List<GiftCertificate> findAll() {
        return template.query(FIND_ALL_SQL, rowMapper);
    }

    @Override
    public boolean delete(GiftCertificate giftCertificate) {
        int update = template.update(DELETE_SQL, giftCertificate.getId());
        return update != 0;
    }

    @Override
    public List<GiftCertificate> findAllByTagName(String tagName) {
        return template.query(FIND_ALL_BY_TAG_NAME, new Object[]{tagName}, rowMapper);
    }

    @Override
    public List<GiftCertificate> findAllByPartNameAndPartDescription(String partName, String partDescription) {
        return template.query(
                FIND_ALL_BY_PART_NAME_AND_PART_DESCRIPTION,
                new Object[]{partName, partDescription},
                rowMapper
        );
    }

    @Override
    public boolean addTag(GiftCertificate giftCertificate, Tag tag) {
        int update = template.update("INSERT INTO gift_certificate_tag (tag_id, gift_certificate_id) VALUES (?,?);", tag.getId(), giftCertificate.getId());
        return update != 0;
    }
}
