package com.educore.studentservice.presentation.rest.request;

import com.educore.studentservice.domain.model.Gender;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    22/07/2026 at 16:09
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

public record CreateStudentRequest(

        @NotBlank
        @Size(max = 100)
        String identityId,

        @NotBlank
        @Pattern(regexp = "^[A-Za-z0-9][A-Za-z0-9_-]{2,29}$")
        String studentCode,

        @NotBlank
        @Size(min = 2, max = 150)
        String fullName,

        @NotNull
        @Past
        LocalDate dateOfBirth,

        @NotNull
        Gender gender,

        @Size(max = 30)
        String phone,

        @Size(max = 500)
        String address,

        @Min(2000)
        @Max(2100)
        int enrollmentYear
) {
}
