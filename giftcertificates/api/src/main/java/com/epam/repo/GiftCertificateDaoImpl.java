package com.epam.repo;

import com.epam.entity.GiftCertificate;
import com.epam.repo.exception.DaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {

    private static final String FIND_BY_ID_SQL = "SELECT id,description,price,createDate,lastUpdateDate,duration FROM gift_certificate WHERE id = ?;";
    private static final String FIND_ALL_SQL = "SELECT id,description,price,createDate,lastUpdateDate,duration FROM gift_certificate;";
    private static final String DELETE_SQL = "DELETE FROM gift_certificate WHERE id=?;";
    private static final String FIND_ALL_BY_TAG_NAME = "SELECT gift_certificate.id,description,price,createDate,lastUpdateDate,duration FROM gift_certificate INNER JOIN gift_certificate_tag ON gift_certificate.id = gift_certificate_tag.gift_certificate_id INNER JOIN tag ON gift_certificate_tag.tag_id = tag.id WHERE tag.name = ?;";
    private static final String FIND_ALL_BY_PART_NAME_AND_PART_DESCRIPTION = "SELECT id,description,price,createDate,lastUpdateDate,duration FROM gift_certificate WHERE name LIKE '%\\?%' AND description LIKE '%\\?%';";
    private static final String CREATE_SQL = "INSERT INTO gift_certificate (id, name, description, price, createDate, lastUpdateDate, duration) VALUES (?,?,?,?,?,?,?);";
    private static final String UPDATE_SQL = "UPDATE gift_certificate SET  name = ?, description = ?, price = ?, createDate = ?, lastUpdateDate = ?, duration = ? WHERE id = ?;";

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

        template.update(
                CREATE_SQL,
                giftCertificate.getId(),
                giftCertificate.getName(),
                giftCertificate.getDescription(),
                giftCertificate.getPrice(),
                giftCertificate.getCreateDate(),
                giftCertificate.getLastUpdateDate()
                , keyHolder
        );

        Number key = keyHolder.getKey();
        if (key != null) {
            giftCertificate.setId(key.longValue());
            return giftCertificate;
        }
        throw new DaoException();
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
}
