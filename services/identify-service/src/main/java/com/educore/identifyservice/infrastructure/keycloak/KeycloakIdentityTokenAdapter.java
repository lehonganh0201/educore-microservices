package com.educore.identifyservice.infrastructure.keycloak;

import com.educore.identifyservice.application.port.out.IdentityTokenProvider;
import com.educore.identifyservice.application.result.AuthenticationTokenResult;
import com.educore.web.exception.BadRequestException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    20/07/2026 at 16:19
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@Component
public class KeycloakIdentityTokenAdapter implements IdentityTokenProvider {

    private final RestClient keycloakRestClient;
    private final KeycloakProperties properties;

    public KeycloakIdentityTokenAdapter(RestClient keycloakRestClient, KeycloakProperties properties) {
        this.keycloakRestClient = keycloakRestClient;
        this.properties = properties;
    }

    @Override
    public AuthenticationTokenResult login(
            String usernameOrEmail,
            String password
    ) {
        MultiValueMap<String, String> form =
                createClientForm();

        form.add("grant_type", "password");
        form.add("username", usernameOrEmail);
        form.add("password", password);
        form.add("scope", "openid profile email");

        try {
            return requestToken(form);
        } catch (RestClientResponseException exception) {
            if (exception.getStatusCode()
                    .is4xxClientError()) {
                throw new BadRequestException("Invalid username or password");
            }

            throw new BadRequestException("Failed to request token from Keycloak");
        }
    }

    @Override
    public AuthenticationTokenResult refresh(
            String refreshToken
    ) {
        MultiValueMap<String, String> form =
                createClientForm();

        form.add("grant_type", "refresh_token");
        form.add("refresh_token", refreshToken);

        try {
            return requestToken(form);
        } catch (RestClientResponseException exception) {
            if (exception.getStatusCode()
                    .is4xxClientError()) {
                throw new BadRequestException("Invalid refresh token");
            }

            throw new BadRequestException("Failed to request token from Keycloak");
        }
    }

    private AuthenticationTokenResult requestToken(
            MultiValueMap<String, String> form
    ) {
        KeycloakTokenResponse response =
                keycloakRestClient
                        .post()
                        .uri(properties.tokenPath())
                        .contentType(
                                MediaType
                                        .APPLICATION_FORM_URLENCODED
                        )
                        .body(form)
                        .retrieve()
                        .body(KeycloakTokenResponse.class);

        if (response == null) {
            throw new BadRequestException("Failed to request token from Keycloak");
        }

        return response.toResult();
    }

    private MultiValueMap<String, String> createClientForm() {
        MultiValueMap<String, String> form =
                new LinkedMultiValueMap<>();

        form.add("client_id", properties.clientId());
        form.add(
                "client_secret",
                properties.clientSecret()
        );

        return form;
    }
}
