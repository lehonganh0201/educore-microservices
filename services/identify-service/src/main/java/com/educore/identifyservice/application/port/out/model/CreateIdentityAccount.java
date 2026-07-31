package com.educore.identifyservice.application.port.out.model;

import com.educore.identifyservice.domain.model.AccountRole;
import com.educore.identifyservice.domain.model.EmailAddress;
import com.educore.identifyservice.domain.model.RawPassword;
import com.educore.identifyservice.domain.model.Username;

import java.util.Set;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    21/07/2026 at 11:32
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

public record CreateIdentityAccount(
        Username username,
        EmailAddress email,
        String firstName,
        String lastName,
        RawPassword password,
        boolean temporaryPassword,
        boolean enabled,
        Set<AccountRole> roles
) {
}