package com.educore.identifyservice.application.command;

import com.educore.identifyservice.domain.model.AccountId;
import com.educore.identifyservice.domain.model.AccountRole;
import com.educore.web.exception.BadRequestException;

import java.util.Set;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    22/07/2026 at 10:34
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

public record ReplaceAccountRolesCommand(
        AccountId accountId,
        Set<AccountRole> roles
) {

    public ReplaceAccountRolesCommand {
        roles = roles == null ? Set.of() : Set.copyOf(roles);

        if (roles.isEmpty()) {
            throw new BadRequestException(
                    "Account must have at least one role"
            );
        }
    }
}
