package com.educore.identifyservice.presentation.rest;

import com.educore.common.dto.ApiResponse;
import com.educore.identifyservice.application.command.LoginCommand;
import com.educore.identifyservice.application.port.in.AuthenticationUseCase;
import com.educore.identifyservice.application.result.AuthenticationTokenResult;
import com.educore.identifyservice.presentation.rest.request.LoginRequest;
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
}
