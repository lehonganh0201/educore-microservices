package com.educore.studentservice.application.result;

import com.educore.studentservice.domain.model.Student;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    22/07/2026 at 15:37
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

public record StudentResult(
        UUID id,
        String identityId,
        String studentCode,
        String fullName,
        LocalDate dateOfBirth,
        String gender,
        String phone,
        String address,
        int enrollmentYear,
        String status,
        Instant createdAt,
        Instant updatedAt
) {

    public static StudentResult from(Student student) {
        return new StudentResult(
                student.id().value(),
                student.identityId().value(),
                student.studentCode().value(),
                student.fullName().value(),
                student.dateOfBirth(),
                student.gender().name(),
                student.phone(),
                student.address(),
                student.enrollmentYear(),
                student.status().name(),
                student.createdAt(),
                student.updatedAt()
        );
    }
}