package com.educore.identifyservice.application.service;

import com.educore.identifyservice.application.command.CreateAccountCommand;
import com.educore.identifyservice.application.port.in.AccountManagementUseCase;
import com.educore.identifyservice.application.port.out.IdentityManagementPort;
import com.educore.identifyservice.application.port.out.model.CreateIdentityAccount;
import com.educore.identifyservice.application.result.AccountResult;
import com.educore.identifyservice.domain.exception.AccountAlreadyExistsException;
import com.educore.identifyservice.domain.model.Account;
import com.educore.identifyservice.domain.model.AccountId;
import org.springframework.stereotype.Service;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    21/07/2026 at 11:02
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@Service
public class AccountManagementService implements AccountManagementUseCase {
    private final IdentityManagementPort identityManagementPort;

    public AccountManagementService(IdentityManagementPort identityManagementPort) {
        this.identityManagementPort = identityManagementPort;
    }

    @Override
    public AccountResult create(CreateAccountCommand command) {
        if (identityManagementPort.existsByUsername(command.username())) {
            throw new AccountAlreadyExistsException(
                    "Username already exists username: " + command.username().value()
            );
        }

        if (identityManagementPort.existsByEmail(command.email())) {
            throw new AccountAlreadyExistsException(
                    "Email already exists email: " + command.email().value()
            );
        }

        Account account = identityManagementPort.create(
                new CreateIdentityAccount(
                        command.username(),
                        command.email(),
                        command.firstName(),
                        command.lastName(),
                        command.password(),
                        command.temporaryPassword(),
                        command.enabled(),
                        command.roles()
                )
        );

        return AccountResult.from(account);
    }

    @Override
    public AccountResult findById(AccountId accountId) {
        return AccountResult.from(
                identityManagementPort.findById(accountId)
        );
    }
}
