package com.educore.identifyservice.application.result;

import com.educore.identifyservice.domain.model.Account;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    21/07/2026 at 11:37
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

public record AccountResult(
        String id,
        String username,
        String email,
        String firstName,
        String lastName,
        String status,
        boolean emailVerified,
        Set<String> roles,
        Instant createdAt
) {

    public static AccountResult from(Account account) {
        return new AccountResult(
                account.id().value(),
                account.username().value(),
                account.email().value(),
                account.firstName(),
                account.lastName(),
                account.status().name(),
                account.emailVerified(),
                account.roles()
                        .stream()
                        .map(Enum::name)
                        .collect(Collectors.toUnmodifiableSet()),
                account.createdAt()
        );
    }
}