package com.educore.identifyservice.presentation.rest;

import com.educore.common.dto.ApiResponse;
import com.educore.common.dto.PageResponse;
import com.educore.identifyservice.application.command.ChangeAccountStatusCommand;
import com.educore.identifyservice.application.command.CreateAccountCommand;
import com.educore.identifyservice.application.command.UpdateAccountCommand;
import com.educore.identifyservice.application.port.in.AccountManagementUseCase;
import com.educore.identifyservice.application.query.SearchAccountsQuery;
import com.educore.identifyservice.application.result.AccountResult;
import com.educore.identifyservice.domain.model.AccountId;
import com.educore.identifyservice.domain.model.EmailAddress;
import com.educore.identifyservice.domain.model.RawPassword;
import com.educore.identifyservice.domain.model.Username;
import com.educore.identifyservice.presentation.rest.request.ChangeAccountStatusRequest;
import com.educore.identifyservice.presentation.rest.request.CreateAccountRequest;
import com.educore.identifyservice.presentation.rest.request.UpdateAccountRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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

    @GetMapping
    public ResponseEntity<PageResponse<AccountResult>> search(
            @RequestParam(defaultValue = "") String keyword,

            @RequestParam(defaultValue = "0") @Min(0) int page,

            @RequestParam(defaultValue = "20") @Min(1) @Max(100) int size
    ) {
        return ResponseEntity.ok(
                accountUseCase.search(new SearchAccountsQuery(
                        keyword,
                        page,
                        size
                ))
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

    @PutMapping("/{accountId}")
    public ResponseEntity<ApiResponse<AccountResult>> update(
            @PathVariable String accountId,

            @Valid @RequestBody UpdateAccountRequest request
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Update account successful",
                        accountUseCase.update(
                                new UpdateAccountCommand(
                                        AccountId.of(accountId),
                                        Username.of(request.username()),
                                        EmailAddress.of(request.email()),
                                        request.firstName(),
                                        request.lastName()
                                ))
                )
        );
    }

    @PatchMapping("/{accountId}/status")
    public ResponseEntity<ApiResponse<AccountResult>> changeStatus(
            @PathVariable String accountId,
            @RequestBody ChangeAccountStatusRequest request
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Update account status successful",
                        accountUseCase.changeStatus(
                                new ChangeAccountStatusCommand(
                                        AccountId.of(accountId),
                                        request.enabled()
                                )
                        )
                )
        );
    }
}
