package com.educore.identifyservice.presentation.rest;

import com.educore.common.dto.ApiResponse;
import com.educore.identifyservice.application.command.LoginCommand;
import com.educore.identifyservice.application.command.LogoutCommand;
import com.educore.identifyservice.application.command.RefreshTokenCommand;
import com.educore.identifyservice.application.port.in.AuthenticationUseCase;
import com.educore.identifyservice.application.result.AuthenticationTokenResult;
import com.educore.identifyservice.presentation.rest.request.LoginRequest;
import com.educore.identifyservice.presentation.rest.request.LogoutRequest;
import com.educore.identifyservice.presentation.rest.request.RefreshTokenRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    20/07/2026 at 16:05
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private final AuthenticationUseCase authenticationUseCase;

    public AuthenticationController(AuthenticationUseCase authenticationUseCase) {
        this.authenticationUseCase = authenticationUseCase;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthenticationTokenResult>> login(
            @Valid @RequestBody LoginRequest request
    ) {
        return ResponseEntity
                .ok(ApiResponse.success(
                                "Login successful",
                                authenticationUseCase.login(
                                        new LoginCommand(
                                                request.usernameOrEmail(),
                                                request.password()
                                        )
                                )
                        )
                );
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<AuthenticationTokenResult>> refresh(
            @Valid @RequestBody RefreshTokenRequest request
    ) {
        return ResponseEntity
                .ok(ApiResponse.success(
                                "Refresh token successful",
                                authenticationUseCase.refresh(
                                        new RefreshTokenCommand(
                                                request.refreshToken()
                                        ))
                        )
                );
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(
            @Valid @RequestBody LogoutRequest request
    ) {
        authenticationUseCase.logout(new LogoutCommand(request.refreshToken()));

        return ResponseEntity
                .ok(ApiResponse.success("Logout successful"));
    }
}
