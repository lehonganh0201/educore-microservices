package com.educore.identifyservice.application.port.in;

import com.educore.common.dto.PageResponse;
import com.educore.identifyservice.application.command.*;
import com.educore.identifyservice.application.query.SearchAccountsQuery;
import com.educore.identifyservice.application.result.AccountResult;
import com.educore.identifyservice.domain.model.AccountId;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    21/07/2026 at 11:02
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public interface AccountManagementUseCase {
    AccountResult create(CreateAccountCommand command);

    AccountResult findById(AccountId accountId);

    PageResponse<AccountResult> search(SearchAccountsQuery query);

    AccountResult update(UpdateAccountCommand command);

    AccountResult changeStatus(ChangeAccountStatusCommand command);

    AccountResult replaceRoles(ReplaceAccountRolesCommand command);

    void resetPassword(ResetAccountPasswordCommand command);
}
