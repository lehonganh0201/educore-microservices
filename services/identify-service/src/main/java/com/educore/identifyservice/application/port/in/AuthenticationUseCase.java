package com.educore.identifyservice.application.port.in;

import com.educore.identifyservice.application.command.LoginCommand;
import com.educore.identifyservice.application.command.RefreshTokenCommand;
import com.educore.identifyservice.application.result.AuthenticationTokenResult;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    20/07/2026 at 16:09
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

public interface AuthenticationUseCase {

    AuthenticationTokenResult login(LoginCommand command);

    AuthenticationTokenResult refresh (
            RefreshTokenCommand command
    );
}