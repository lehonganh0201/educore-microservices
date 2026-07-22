package com.educore.identifyservice.presentation.rest.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    22/07/2026 at 9:51
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

public record UpdateAccountRequest(

        @NotBlank
        @Size(min = 3, max = 100)
        String username,

        @NotBlank
        @Email
        String email,

        @Size(max = 100)
        String firstName,

        @Size(max = 100)
        String lastName
) {
}