package com.educore.identifyservice.domain.model;

import java.time.Instant;
import java.util.Set;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    21/07/2026 at 10:57
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

public record Account(
        AccountId id,
        Username username,
        EmailAddress email,
        String firstName,
        String lastName,
        AccountStatus status,
        boolean emailVerified,
        Set<AccountRole> roles,
        Instant createdAt
) {

    public Account {
        roles = roles == null
                ? Set.of()
                : Set.copyOf(roles);

        firstName = firstName == null ? "" : firstName;
        lastName = lastName == null ? "" : lastName;
    }
}