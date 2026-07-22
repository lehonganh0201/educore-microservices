package com.educore.identifyservice.application.command;

import com.educore.identifyservice.domain.model.AccountId;
import com.educore.identifyservice.domain.model.RawPassword;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    22/07/2026 at 10:41
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

public record ResetAccountPasswordCommand(
        AccountId accountId,
        RawPassword password,
        boolean temporary
) {
}
