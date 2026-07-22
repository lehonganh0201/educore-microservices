package com.educore.studentservice.application.command;

import com.educore.studentservice.domain.model.Gender;

import java.time.LocalDate;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    22/07/2026 at 15:31
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

public record CreateStudentCommand(
        String identityId,
        String studentCode,
        String fullName,
        LocalDate dateOfBirth,
        Gender gender,
        String phone,
        String address,
        int enrollmentYear
) {
}