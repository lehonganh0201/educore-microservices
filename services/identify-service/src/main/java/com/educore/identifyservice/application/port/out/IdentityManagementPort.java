package com.educore.identifyservice.application.port.out;

import com.educore.identifyservice.application.port.out.model.AccountSearchCriteria;
import com.educore.identifyservice.application.port.out.model.CreateIdentityAccount;
import com.educore.identifyservice.application.port.out.model.UpdateIdentityAccount;
import com.educore.identifyservice.domain.model.*;
import org.springframework.data.domain.Page;

import java.util.Set;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    21/07/2026 at 11:35
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public interface IdentityManagementPort {
    boolean existsByUsername(Username username);

    boolean existsByEmail(EmailAddress email);

    boolean existsByUsernameOtherThan(
            Username username,
            AccountId accountId
    );

    boolean existsByEmailOtherThan(
            EmailAddress email,
            AccountId accountId
    );

    Account create(CreateIdentityAccount account);

    Account findById(AccountId accountId);

    Page<Account> search(
            AccountSearchCriteria criteria
    );

    Account update(UpdateIdentityAccount account);

    Account changeStatus(
            AccountId accountId,
            boolean enabled
    );

    Account replaceRoles(
            AccountId accountId,
            Set<AccountRole> roles
    );

    void resetPassword(
            AccountId accountId,
            RawPassword password,
            boolean temporary
    );

    void logoutSessions(AccountId accountId);
}
