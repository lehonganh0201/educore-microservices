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

public record Username(String value) {

    public Username {
        Objects.requireNonNull(value, "Username must not be null");

        value = value.trim().toLowerCase(Locale.ROOT);

        if (value.length() < 3 || value.length() > 100) {
            throw new IllegalArgumentException(
                    "Username must contain 3 to 100 characters"
            );
        }
    }

    public static Username of(String value) {
        return new Username(value);
    }
}