package com.educore.studentservice.application.command;

import com.educore.studentservice.domain.model.Gender;

import java.time.LocalDate;
import java.util.UUID;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    22/07/2026 at 17:12
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

public record UpdateStudentCommand(
        UUID studentId,
        String fullName,
        LocalDate dateOfBirth,
        Gender gender,
        String phone,
        String address,
        int enrollmentYear
) {
}
