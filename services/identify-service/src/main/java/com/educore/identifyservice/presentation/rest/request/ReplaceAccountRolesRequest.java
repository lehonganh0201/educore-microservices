package com.educore.identifyservice.presentation.rest.request;

import com.educore.identifyservice.domain.model.AccountRole;
import jakarta.validation.constraints.NotEmpty;

import java.util.Set;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    22/07/2026 at 10:33
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

public record ReplaceAccountRolesRequest(

        @NotEmpty
        Set<AccountRole> roles
) {
}