package com.educore.identifyservice.application.service;

import com.educore.common.dto.PageResponse;
import com.educore.data.pagination.SpringPageResponseMapper;
import com.educore.identifyservice.application.command.*;
import com.educore.identifyservice.application.port.in.AccountManagementUseCase;
import com.educore.identifyservice.application.port.out.IdentityManagementPort;
import com.educore.identifyservice.application.port.out.model.AccountSearchCriteria;
import com.educore.identifyservice.application.port.out.model.CreateIdentityAccount;
import com.educore.identifyservice.application.port.out.model.UpdateIdentityAccount;
import com.educore.identifyservice.application.query.SearchAccountsQuery;
import com.educore.identifyservice.application.result.AccountResult;
import com.educore.identifyservice.domain.exception.AccountAlreadyExistsException;
import com.educore.identifyservice.domain.model.Account;
import com.educore.identifyservice.domain.model.AccountId;
import org.springframework.data.domain.Page;
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

    @Override
    public PageResponse<AccountResult> search(SearchAccountsQuery query) {
        Page<AccountResult> resultPage = identityManagementPort.search(
                new AccountSearchCriteria(
                        query.keyword(),
                        query.page(),
                        query.size()
                )
        ).map(AccountResult::from);

        return SpringPageResponseMapper.from(resultPage);
    }

    @Override
    public AccountResult update(
            UpdateAccountCommand command
    ) {
        if (identityManagementPort.existsByUsernameOtherThan(
                        command.username(),
                        command.accountId())) {
            throw new AccountAlreadyExistsException(
                    "Username already exists username: " + command.username().value()
            );
        }

        if (identityManagementPort.existsByEmailOtherThan(
                        command.email(),
                        command.accountId())) {
            throw new AccountAlreadyExistsException(
                    "Email already exists email: " + command.email().value()
            );
        }

        Account updated = identityManagementPort.update(
                new UpdateIdentityAccount(
                        command.accountId(),
                        command.username(),
                        command.email(),
                        command.firstName(),
                        command.lastName()
                )
        );

        return AccountResult.from(updated);
    }

    @Override
    public AccountResult changeStatus(
            ChangeAccountStatusCommand command
    ) {
        Account account = identityManagementPort.changeStatus(
                        command.accountId(),
                        command.enabled()
                );

        return AccountResult.from(account);
    }

    @Override
    public AccountResult replaceRoles(
            ReplaceAccountRolesCommand command
    ) {
        Account account = identityManagementPort.replaceRoles(
                        command.accountId(),
                        command.roles()
                );

        return AccountResult.from(account);
    }

    @Override
    public void resetPassword(
            ResetAccountPasswordCommand command
    ) {
        identityManagementPort.resetPassword(
                command.accountId(),
                command.password(),
                command.temporary()
        );
    }
}
