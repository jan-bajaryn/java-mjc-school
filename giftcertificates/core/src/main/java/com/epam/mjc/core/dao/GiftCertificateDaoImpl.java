package com.epam.mjc.core.dao;

import com.epam.mjc.api.dao.GiftCertificateDao;
import com.epam.mjc.api.domain.GiftCertificate;
import com.epam.mjc.api.domain.GiftCertificate_;
import com.epam.mjc.api.util.SearchParams;
import com.epam.mjc.core.dao.builder.SearchQueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {


    private static final Logger log = LoggerFactory.getLogger(GiftCertificateDaoImpl.class);


    @PersistenceContext
    private final EntityManager entityManager;

    @Autowired
    public GiftCertificateDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    @PrePersist
    public GiftCertificate create(GiftCertificate giftCertificate) {

        entityManager.persist(giftCertificate);
        return giftCertificate;

    }

    @Override
    @Transactional
    @PreUpdate
    public void update(GiftCertificate giftCertificate) {
        entityManager.merge(giftCertificate);
    }

    @Override
    public Optional<GiftCertificate> findById(Long id) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> criteriaQuery = criteriaBuilder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> root = criteriaQuery.from(GiftCertificate.class);
        criteriaQuery.select(root).distinct(true).where(criteriaBuilder.equal(root.get(GiftCertificate_.id), id));

        try {
            return Optional.ofNullable(entityManager.createQuery(criteriaQuery).getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }


    @Override
    @Transactional
    public boolean delete(GiftCertificate giftCertificate) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaDelete<GiftCertificate> criteriaDelete = criteriaBuilder.createCriteriaDelete(GiftCertificate.class);
        Root<GiftCertificate> root = criteriaDelete.from(GiftCertificate.class);
        criteriaDelete.where(criteriaBuilder.equal(root.get(GiftCertificate_.id), giftCertificate.getId()));

        int update = entityManager.createQuery(criteriaDelete).executeUpdate();
        return update != 0;
    }

    @Override
    public List<GiftCertificate> search(SearchParams searchParams, Integer begin, Integer pageSize) {
        CriteriaQuery<GiftCertificate> criteriaQuery = SearchQueryBuilder.builder()
                .searchParams(searchParams, entityManager.getCriteriaBuilder())
                .build();

        return entityManager.createQuery(criteriaQuery)
                .setFirstResult(begin)
                .setMaxResults(pageSize)
                .getResultList();

    }

    @Override
    public Optional<GiftCertificate> findByName(String name) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> criteriaQuery = criteriaBuilder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> root = criteriaQuery.from(GiftCertificate.class);
        criteriaQuery.select(root).distinct(true).where(criteriaBuilder.equal(root.get(GiftCertificate_.name), name));

        try {
            return Optional.ofNullable(entityManager.createQuery(criteriaQuery).getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
