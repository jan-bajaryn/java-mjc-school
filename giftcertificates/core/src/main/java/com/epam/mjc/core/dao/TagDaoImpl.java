package com.epam.mjc.core.dao;

import com.epam.mjc.api.dao.TagDao;
import com.epam.mjc.api.domain.Tag;
import com.epam.mjc.api.domain.Tag_;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class TagDaoImpl implements TagDao {
    private static final Logger log = LoggerFactory.getLogger(TagDaoImpl.class);
    // lang=SQL
    private static final String FIND_POPULAR_TAG =
            "with rich_user(id, price) as\n" +
                    "         (select usr.id, sum(o.price) as sm\n" +
                    "          from usr\n" +
                    "                   inner join orders o on usr.id = o.user_id\n" +
                    "          group by usr.id\n" +
                    "          order by sm desc\n" +
                    "          limit 1)\n" +
                    "   ,\n" +
                    "     tag_count_usages(id, cnt) as\n" +
                    "    (select tag.id, sum(gco.count)\n" +
                    "     from tag\n" +
                    "              inner join gift_certificate_tag gct on tag.id = gct.tag_id\n" +
                    "              inner join gift_certificate gc on gct.gift_certificate_id = gc.id\n" +
                    "              inner join gift_certificate_order gco on gc.id = gco.gift_certificate_id\n" +
                    "              inner join orders o2 on gco.order_id = o2.id\n" +
                    "              inner join usr u on o2.user_id = u.id\n" +
                    "     where u.id = (select id from rich_user)\n" +
                    "     group by tag.id\n" +
                    "     order by sum(gco.count) desc\n" +
                    "     limit 1)\n" +
                    " select tag.id, tag.name from tag where tag.id = (select tag_count_usages.id from tag_count_usages)";


    @PersistenceContext
    private final EntityManager entityManager;


    @Autowired
    public TagDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Tag> findAll(Integer begin, Integer pageSize) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> criteriaQuery = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> root = criteriaQuery.from(Tag.class);
        criteriaQuery.select(root);
        return entityManager.createQuery(criteriaQuery)
                .setFirstResult(begin)
                .setMaxResults(pageSize)
                .getResultList();
    }

    @Transactional
    @Override
    public Tag create(Tag tag) {

        entityManager.persist(tag);
        return tag;

    }

    @Transactional
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
    public Tag findMostPopularTagIdOfUserHigherCostOrders() {
        return (Tag) entityManager.createNativeQuery(FIND_POPULAR_TAG, Tag.class).getSingleResult();
    }

}
