package com.educore.studentservice.application.command;

import com.educore.studentservice.domain.model.StudentStatus;

import java.util.UUID;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    22/07/2026 at 17:21
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

public record ChangeStudentStatusCommand(
        UUID studentId,
        StudentStatus status
) {
}
