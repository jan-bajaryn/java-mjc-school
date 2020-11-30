package com.epam.mjc.core.dao;

import com.epam.mjc.api.dao.UserDao;
import com.epam.mjc.api.domain.User;
import com.epam.mjc.api.domain.User_;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {

    private static final Logger log = LoggerFactory.getLogger(UserDaoImpl.class);

    @PersistenceContext
    private final EntityManager entityManager;

    @Autowired
    public UserDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<User> findAll(Integer begin, Integer pageSize) {
        log.debug("findAll: begin = {}", begin);
        log.debug("findAll: pageSize = {}", pageSize);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.select(root);
        List<User> resultList = entityManager
                .createQuery(criteriaQuery.orderBy(criteriaBuilder.asc(root.get(User_.id))))
                .setFirstResult(begin)
                .setMaxResults(pageSize)
                .getResultList();

        log.debug("findAll: resultList = {}", resultList);
        return resultList;
    }

    @Override
    public Optional<User> findById(Long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.select(root).distinct(true).where(criteriaBuilder.equal(root.get(User_.id), id));

        try {
            return Optional.ofNullable(entityManager.createQuery(criteriaQuery).getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
