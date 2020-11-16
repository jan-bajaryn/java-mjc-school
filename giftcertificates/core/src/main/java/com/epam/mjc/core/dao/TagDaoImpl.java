package com.epam.mjc.core.dao;

import com.epam.mjc.api.dao.TagDao;
import com.epam.mjc.api.domain.Tag;
import com.epam.mjc.api.domain.Tag_;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class TagDaoImpl implements TagDao {
    private static final String FIND_BY_CERTIFICATE_ID = "SELECT tag.id, tag.name FROM tag INNER JOIN gift_certificate_tag ON tag.id = gift_certificate_tag.tag_id WHERE gift_certificate_id = ?;";
    private static final String FIND_BY_TAG_NAME = "SELECT id, name FROM tag WHERE name =?;";
    private static final String FIND_EXISTING_SQL = "SELECT id,name FROM tag WHERE name IN (%s)";
    private static final String COMMA = ",";
    private static final String QUESTION_MARK = "?";
    private final JdbcTemplate template;
    private final RowMapper<Tag> rowMapper;
    private static final Logger log = LoggerFactory.getLogger(TagDaoImpl.class);

    private final EntityManager entityManager;

    @Autowired
    public TagDaoImpl(final JdbcTemplate template, final RowMapper<Tag> rowMapper, EntityManager entityManager) {
        this.template = template;
        this.rowMapper = rowMapper;
        this.entityManager = entityManager;
    }

    @Override
    public List<Tag> findAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> criteriaQuery = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> root = criteriaQuery.from(Tag.class);
        criteriaQuery.select(root);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Transactional
    @Override
    public Tag create(Tag tag) {

        entityManager.persist(tag);
        return tag;

    }

    @Override
    public boolean delete(Tag tag) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaDelete<Tag> criteriaDelete = criteriaBuilder.createCriteriaDelete(Tag.class);
        Root<Tag> root = criteriaDelete.from(Tag.class);
        criteriaDelete.where(criteriaBuilder.equal(root.get(Tag_.id), tag.getId()));

        int update = entityManager.createQuery(criteriaDelete).executeUpdate();
        return update != 0;
    }

    @Override
    public Optional<Tag> findById(Long id) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> criteriaQuery = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> root = criteriaQuery.from(Tag.class);
        criteriaQuery.select(root).distinct(true).where(criteriaBuilder.equal(root.get(Tag_.id), id));

        try {
            return Optional.ofNullable(entityManager.createQuery(criteriaQuery).getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }

    }

    // TODO CHECK IF NEED IN FUTURE
    @Override
    public List<Tag> findAllByGiftCertificateId(Long id) {
        return template.query(FIND_BY_CERTIFICATE_ID, new Object[]{id}, rowMapper);
    }

    @Override
    public Optional<Tag> findByTagName(String name) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> criteriaQuery = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> root = criteriaQuery.from(Tag.class);
        criteriaQuery.select(root).distinct(true).where(criteriaBuilder.equal(root.get(Tag_.name), name));
        try {
            return Optional.ofNullable(entityManager.createQuery(criteriaQuery).getSingleResult());
        } catch (NoResultException e) {
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
}
