package com.epam.mjc.core.dao.specification;

import com.epam.mjc.api.domain.GiftCertificate;
import com.epam.mjc.api.domain.GiftCertificate_;
import com.epam.mjc.api.domain.Tag;
import com.epam.mjc.api.domain.Tag_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class GiftCertificateSpecification {


    public static Specification<GiftCertificate> search(String partName, String partDescription, List<String> tagNames) {
        return Specification.where(partName(partName))
                .and(tagNames(tagNames))
                .and(partDescription(partDescription));
    }

    public static Specification<GiftCertificate> partName(String partName) {
        return (r, cq, cb) -> {
            if (partName != null) {
                return cb.or(cb.like(r.get(GiftCertificate_.name), "%" + partName + "%"));
            }
            return cb.conjunction();
        };
    }

    public static Specification<GiftCertificate> partDescription(String partDescription) {
        return (r, cq, cb) -> {
            if (partDescription != null) {
                return cb.or(cb.like(r.get(GiftCertificate_.description), "%" + partDescription + "%"));
            }
            return cb.conjunction();
        };
    }

    public static Specification<GiftCertificate> tagNames(List<String> tagNames) {
        return (r, cq, cb) -> {
            if (tagNames != null && !tagNames.isEmpty()) {
                List<Predicate> predicates = new ArrayList<>();
                for (String tagName : tagNames) {
                    Join<GiftCertificate, Tag> join = r.join(GiftCertificate_.tags, JoinType.INNER);
                    Predicate tagPredicate = cb.equal(join.get(Tag_.name), tagName);
                    predicates.add(tagPredicate);
                }
                return cb.and(predicates.toArray(new Predicate[0]));
            }
            return cb.conjunction();
        };
    }


}
