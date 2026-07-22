package com.educore.identifyservice.application.port.out.model;

import com.educore.identifyservice.domain.model.AccountId;
import com.educore.identifyservice.domain.model.EmailAddress;
import com.educore.identifyservice.domain.model.Username;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    22/07/2026 at 9:55
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

public record UpdateIdentityAccount(
        AccountId accountId,
        EmailAddress email,
        String firstName,
        String lastName
) {
}
