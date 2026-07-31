package com.educore.studentservice.application.port.out.model;

import com.educore.studentservice.domain.model.Gender;
import com.educore.studentservice.domain.model.StudentStatus;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    22/07/2026 at 16:34
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

public record StudentSearchCriteria(
        String keyword,
        StudentStatus status,
        Gender gender,
        Integer enrollmentYear,
        int page,
        int size
) {
}