package com.educore.identifyservice.application.command;

import com.educore.identifyservice.domain.model.AccountId;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    22/07/2026 at 10:19
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

public record ChangeAccountStatusCommand(
        AccountId accountId,
        boolean enabled
) {
}