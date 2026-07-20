package com.educore.identifyservice.application.service;

import com.educore.identifyservice.application.command.LoginCommand;
import com.educore.identifyservice.application.port.in.AuthenticationUseCase;
import com.educore.identifyservice.application.port.out.IdentityTokenProvider;
import com.educore.identifyservice.application.result.AuthenticationTokenResult;
import org.springframework.stereotype.Service;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    20/07/2026 at 16:11
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@Service
public class AuthenticationApplicationService implements AuthenticationUseCase {
    private final IdentityTokenProvider identityTokenProvider;

    public AuthenticationApplicationService(IdentityTokenProvider identityTokenProvider) {
        this.identityTokenProvider = identityTokenProvider;
    }

    @Override
    public AuthenticationTokenResult login(LoginCommand command) {
        return identityTokenProvider.login(
                command.usernameOrEmail(),
                command.password()
        );
    }
}
