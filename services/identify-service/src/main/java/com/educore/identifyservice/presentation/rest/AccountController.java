package com.educore.identifyservice.presentation.rest;

import com.educore.common.dto.ApiResponse;
import com.educore.identifyservice.application.command.CreateAccountCommand;
import com.educore.identifyservice.application.port.in.AccountManagementUseCase;
import com.educore.identifyservice.application.result.AccountResult;
import com.educore.identifyservice.domain.model.AccountId;
import com.educore.identifyservice.domain.model.EmailAddress;
import com.educore.identifyservice.domain.model.RawPassword;
import com.educore.identifyservice.domain.model.Username;
import com.educore.identifyservice.presentation.rest.request.CreateAccountRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    21/07/2026 at 11:00
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@RestController
@RequestMapping("/api/v1/accounts")
@Validated
@PreAuthorize("hasRole('ADMIN')")
public class AccountController {
    private final AccountManagementUseCase accountUseCase;

    public AccountController(AccountManagementUseCase accountUseCase) {
        this.accountUseCase = accountUseCase;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<AccountResult>> create(
            @Valid @RequestBody CreateAccountRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.success(
                        "Create account success",
                        accountUseCase.create(new CreateAccountCommand(
                                Username.of(request.username()),
                                EmailAddress.of(request.email()),
                                request.firstName(),
                                request.lastName(),
                                new RawPassword(request.password()),
                                request.temporaryPassword(),
                                request.enabled(),
                                request.roles())
                        )
                )
        );
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<ApiResponse<AccountResult>> findById(
            @PathVariable String accountId
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Find account by id success",
                        accountUseCase.findById(
                                AccountId.of(accountId)
                        )
                )
        );
    }
}
