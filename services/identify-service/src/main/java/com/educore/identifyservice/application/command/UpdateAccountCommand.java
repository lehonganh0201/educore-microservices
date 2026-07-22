package com.educore.identifyservice.application.command;

import com.educore.identifyservice.domain.model.AccountId;
import com.educore.identifyservice.domain.model.EmailAddress;
import com.educore.identifyservice.domain.model.Username;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    22/07/2026 at 9:52
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

public record UpdateAccountCommand(
        AccountId accountId,
        Username username,
        EmailAddress email,
        String firstName,
        String lastName
) {
}