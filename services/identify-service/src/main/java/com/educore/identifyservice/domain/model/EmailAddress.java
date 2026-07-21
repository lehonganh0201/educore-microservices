package com.educore.identifyservice.domain.model;

import java.util.Locale;
import java.util.Objects;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    21/07/2026 at 10:59
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

public record EmailAddress(String value) {

    public EmailAddress {
        Objects.requireNonNull(value, "Email must not be null");

        value = value.trim().toLowerCase(Locale.ROOT);

        if (value.isBlank() || !value.contains("@")) {
            throw new IllegalArgumentException("Email is invalid");
        }
    }

    public static EmailAddress of(String value) {
        return new EmailAddress(value);
    }
}