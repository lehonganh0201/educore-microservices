package com.educore.identifyservice.application.port.out;

import com.educore.identifyservice.application.result.AuthenticationTokenResult;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    20/07/2026 at 16:10
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public interface IdentityTokenProvider {
    AuthenticationTokenResult login(
            String usernameOrEmail,
            String password
    );
}
