package com.example.team.common;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class GenericSpecification<t> {
    public Specification<t> inData(String targetField, List<?> searchData) {
        return new Specification<t>() {
            @Override
            public Predicate toPredicate(Root<t> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return root.get(targetField).in(searchData);
            }
        };
    }

    public Specification<t> equal(String targetField, Object x) {
        return new Specification<t>() {
            @Override
            public Predicate toPredicate(Root<t> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get(targetField), x);
            }
        };
    }

    public Specification<t> like(String targetField, String str) {
        return new Specification<t>() {
            @Override
            public Predicate toPredicate(Root<t> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.like(root.get(targetField), "%" + str + "%");
            }
        };
    }
}
