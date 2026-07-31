package com.educore.studentservice.infrastructure.persistence.jpa;

import com.educore.studentservice.application.port.out.model.StudentSearchCriteria;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    22/07/2026 at 16:57
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

public final class StudentJpaSpecification {

    private StudentJpaSpecification() {
    }

    public static Specification<StudentJpaEntity> from(
            StudentSearchCriteria criteria
    ) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (criteria.keyword() != null && !criteria.keyword().isBlank()) {

                String keyword = "%"
                        + criteria.keyword()
                        .toLowerCase(Locale.ROOT)
                        + "%";

                predicates.add(
                        criteriaBuilder.or(
                                criteriaBuilder.like(
                                        criteriaBuilder.lower(
                                                root.get(
                                                        "studentCode"
                                                )
                                        ),
                                        keyword
                                ),
                                criteriaBuilder.like(
                                        criteriaBuilder.lower(
                                                root.get(
                                                        "fullName"
                                                )
                                        ),
                                        keyword
                                )
                        )
                );
            }

            if (criteria.status() != null) {
                predicates.add(
                        criteriaBuilder.equal(
                                root.get("status"),
                                criteria.status()
                        )
                );
            }

            if (criteria.gender() != null) {
                predicates.add(
                        criteriaBuilder.equal(
                                root.get("gender"),
                                criteria.gender()
                        )
                );
            }

            if (criteria.enrollmentYear() != null) {
                predicates.add(
                        criteriaBuilder.equal(
                                root.get("enrollmentYear"),
                                criteria.enrollmentYear()
                        )
                );
            }

            return criteriaBuilder.and(
                    predicates.toArray(
                            Predicate[]::new
                    )
            );
        };
    }
}
