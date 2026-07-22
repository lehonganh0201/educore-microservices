package com.educore.studentservice.presentation.rest.request;

import jakarta.validation.constraints.Size;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    22/07/2026 at 17:39
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

public record UpdateMyStudentProfileRequest(

        @Size(max = 30)
        String phone,

        @Size(max = 500)
        String address
) {
}
