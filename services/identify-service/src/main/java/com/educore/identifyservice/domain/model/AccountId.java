package com.educore.identifyservice.domain.model;

import java.util.Objects;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    21/07/2026 at 10:57
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

public record AccountId(String value) {

    public AccountId {
        Objects.requireNonNull(value, "Account ID must not be null");

        value = value.trim();

        if (value.isBlank()) {
            throw new IllegalArgumentException(
                    "Account ID must not be blank"
            );
        }
    }

    public static AccountId of(String value) {
        return new AccountId(value);
    }
}