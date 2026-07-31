package com.educore.identifyservice.presentation.rest.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    22/07/2026 at 10:40
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

public record ResetAccountPasswordRequest(

        @NotBlank
        @Size(min = 8, max = 100)
        String password,

        boolean temporary
) {
}
