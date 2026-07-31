package com.educore.identifyservice.infrastructure.keycloak;

import com.educore.identifyservice.application.result.AuthenticationTokenResult;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    20/07/2026 at 16:18
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

public record KeycloakTokenResponse(

        @JsonProperty("access_token")
        String accessToken,

        @JsonProperty("refresh_token")
        String refreshToken,

        @JsonProperty("token_type")
        String tokenType,

        @JsonProperty("expires_in")
        long expiresIn,

        @JsonProperty("refresh_expires_in")
        long refreshExpiresIn,

        String scope,

        @JsonProperty("session_state")
        String sessionState,

        @JsonProperty("id_token")
        String idToken
) {

    public AuthenticationTokenResult toResult() {
        return new AuthenticationTokenResult(
                accessToken,
                refreshToken,
                tokenType,
                expiresIn,
                refreshExpiresIn,
                scope,
                sessionState,
                idToken
        );
    }
}
