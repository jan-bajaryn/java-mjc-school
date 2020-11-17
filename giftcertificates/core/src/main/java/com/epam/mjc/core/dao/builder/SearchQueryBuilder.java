package com.epam.mjc.core.dao.builder;

import com.epam.mjc.api.domain.GiftCertificate;
import com.epam.mjc.api.domain.GiftCertificate_;
import com.epam.mjc.api.domain.Tag;
import com.epam.mjc.api.domain.Tag_;
import com.epam.mjc.api.util.SearchParams;
import com.epam.mjc.api.util.sort.SortParam;
import com.epam.mjc.api.util.sort.SortParams;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Component
public class SearchQueryBuilder {

    private List<String> tagNames;
    private String partName;
    private String partDescription;
    private SortParams sortParams;

    private final List<Predicate> predicates = new ArrayList<>();
    private final List<Order> orders = new ArrayList<>();
    private CriteriaBuilder criteriaBuilder;
    private Root<GiftCertificate> root;

    public SearchQueryBuilder searchParams(SearchParams searchParams,CriteriaBuilder criteriaBuilder) {
        this.criteriaBuilder = criteriaBuilder;
        this.tagNames = searchParams.getTagNames();
        this.partName = searchParams.getPartName();
        this.partDescription = searchParams.getPartDescription();
        this.sortParams = searchParams.getSortParams();
        return this;
    }

    public CriteriaQuery<GiftCertificate> build() {

        CriteriaQuery<GiftCertificate> criteriaQuery = criteriaBuilder.createQuery(GiftCertificate.class);
        this.root = criteriaQuery.from(GiftCertificate.class);

        tagName();
        partName();
        partDescription();
        sortParams();

        return criteriaQuery.select(root)
                .distinct(true)
                .where(criteriaBuilder.and(predicates.toArray(new Predicate[0])))
                .orderBy(orders.toArray(new Order[0]));
    }

    private void tagName() {
        if (this.tagNames != null && !this.tagNames.isEmpty()) {
            for (String tagName : this.tagNames) {
                Join<GiftCertificate, Tag> join = root.join(GiftCertificate_.tags, JoinType.INNER);
                Predicate tagPredicate = criteriaBuilder.equal(join.get(Tag_.name), tagName);
                predicates.add(tagPredicate);
            }
        }
    }

    private void partName() {
        if (this.partName != null) {
            Predicate or = criteriaBuilder.or(criteriaBuilder.like(root.get(GiftCertificate_.name), "%" + this.partName + "%"));
            predicates.add(or);
        }
    }

    private void partDescription() {
        if (this.partDescription != null) {
            Predicate or = criteriaBuilder.or(criteriaBuilder.like(root.get(GiftCertificate_.description), "%" + this.partDescription + "%"));
            predicates.add(or);
        }
    }

    private void sortParams() {
        if (this.sortParams != null) {
            for (SortParam sortParam : sortParams.getSortParams()) {
                if (sortParam.isAsc()) {
                    orders.add(criteriaBuilder.asc(root.get(sortParam.getFieldName().getColumnName())));
                } else {
                    orders.add(criteriaBuilder.desc(root.get(sortParam.getFieldName().getColumnName())));
                }
            }
        }
    }


    public static SearchQueryBuilder builder() {
        return new SearchQueryBuilder();
    }

}
