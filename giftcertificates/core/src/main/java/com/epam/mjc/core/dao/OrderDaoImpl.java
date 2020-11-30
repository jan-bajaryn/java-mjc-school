package com.epam.mjc.core.dao;

import com.epam.mjc.api.dao.OrderDao;
import com.epam.mjc.api.domain.Order;
import com.epam.mjc.api.domain.Order_;
import com.epam.mjc.api.domain.User;
import com.epam.mjc.api.domain.User_;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderDaoImpl implements OrderDao {
    @PersistenceContext
    private final EntityManager entityManager;

    @Autowired
    public OrderDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Order> search(Long userId, Integer begin, Integer pageSize) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);

        CriteriaQuery<Order> resultCriteria = criteriaQuery.select(root)
                .distinct(true);

        resultCriteria = filterIfIdNotNull(userId, criteriaBuilder, root, resultCriteria);

        return entityManager.createQuery(resultCriteria.orderBy(criteriaBuilder.asc(root.get(Order_.id))))
                .setFirstResult(begin)
                .setMaxResults(pageSize)
                .getResultList();
    }

    private CriteriaQuery<Order> filterIfIdNotNull(Long userId, CriteriaBuilder criteriaBuilder, Root<Order> root, CriteriaQuery<Order> resultCriteria) {
        if (userId != null) {
            Join<Order, User> join = root.join(Order_.user, JoinType.INNER);
            Predicate predicate = criteriaBuilder.equal(join.get(User_.id), userId);
            resultCriteria = resultCriteria.where(criteriaBuilder.and(predicate));
        }
        return resultCriteria;
    }

    @Override
    public Optional<Order> findById(Long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);
        criteriaQuery.select(root).distinct(true).where(criteriaBuilder.equal(root.get(Order_.id), id));

        try {
            return Optional.ofNullable(entityManager.createQuery(criteriaQuery).getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public Order create(Order order) {
        entityManager.persist(order);
        return order;
    }
}
