package com.educore.studentservice.application.query;

import com.educore.studentservice.domain.model.Gender;
import com.educore.studentservice.domain.model.StudentStatus;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    22/07/2026 at 16:32
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

public record SearchStudentsQuery(
        String keyword,
        StudentStatus status,
        Gender gender,
        Integer enrollmentYear,
        int page,
        int size
) {

    public SearchStudentsQuery {
        keyword = keyword == null
                ? ""
                : keyword.trim();

        if (page < 0) {
            throw new IllegalArgumentException(
                    "Page must not be negative"
            );
        }

        if (size < 1 || size > 100) {
            throw new IllegalArgumentException(
                    "Page size must be between 1 and 100"
            );
        }
    }
}