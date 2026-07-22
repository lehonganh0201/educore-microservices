package com.educore.studentservice.presentation.rest.request;

import com.educore.studentservice.domain.model.StudentStatus;
import jakarta.validation.constraints.NotNull;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    22/07/2026 at 17:20
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

public record ChangeStudentStatusRequest(

        @NotNull
        StudentStatus status
) {
}