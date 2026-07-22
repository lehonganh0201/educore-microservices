package com.educore.studentservice.domain.model;

import java.util.Objects;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    22/07/2026 at 15:08
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

public record IdentityId(String value) {

    public IdentityId {
        Objects.requireNonNull(
                value,
                "Identity ID must not be null"
        );

        value = value.trim();

        if (value.isBlank()) {
            throw new IllegalArgumentException(
                    "Identity ID must not be blank"
            );
        }

        if (value.length() > 100) {
            throw new IllegalArgumentException(
                    "Identity ID must not exceed 100 characters"
            );
        }
    }

    public static IdentityId of(String value) {
        return new IdentityId(value);
    }
}
