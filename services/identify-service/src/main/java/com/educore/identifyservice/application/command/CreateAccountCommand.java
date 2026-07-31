package com.educore.identifyservice.application.command;

import com.educore.identifyservice.domain.model.AccountRole;
import com.educore.identifyservice.domain.model.EmailAddress;
import com.educore.identifyservice.domain.model.RawPassword;
import com.educore.identifyservice.domain.model.Username;

import java.util.Set;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    21/07/2026 at 11:31
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

public record CreateAccountCommand(
        Username username,
        EmailAddress email,
        String firstName,
        String lastName,
        RawPassword password,
        boolean temporaryPassword,
        boolean enabled,
        Set<AccountRole> roles
) {

    public CreateAccountCommand {
        roles = roles == null ? Set.of() : Set.copyOf(roles);

        if (roles.isEmpty()) {
            throw new IllegalArgumentException(
                    "Account must have at least one role"
            );
        }
    }
}
