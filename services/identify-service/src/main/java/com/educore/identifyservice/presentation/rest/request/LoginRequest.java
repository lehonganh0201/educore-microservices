package com.educore.identifyservice.presentation.rest.request;

import jakarta.validation.constraints.NotBlank;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    20/07/2026 at 16:06
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

public record LoginRequest(

        @NotBlank
        String usernameOrEmail,

        @NotBlank
        String password
) {
}