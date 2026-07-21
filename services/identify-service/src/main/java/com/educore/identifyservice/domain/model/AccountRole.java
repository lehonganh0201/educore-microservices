package com.educore.identifyservice.domain.model;

import java.util.Arrays;
import java.util.Optional;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    21/07/2026 at 10:58
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

public enum AccountRole {
    ADMIN,
    LECTURER,
    STUDENT;

    public static Optional<AccountRole> fromKeycloakRole(
            String roleName
    ) {
        return Arrays.stream(values())
                .filter(role -> role.name().equals(roleName))
                .findFirst();
    }
}