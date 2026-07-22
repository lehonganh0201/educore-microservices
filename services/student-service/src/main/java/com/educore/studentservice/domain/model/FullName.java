package com.educore.studentservice.domain.model;

import java.util.Objects;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    22/07/2026 at 15:09
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

public record FullName(String value) {

    public FullName {
        Objects.requireNonNull(
                value,
                "Full name must not be null"
        );

        value = value.trim()
                .replaceAll("\\s+", " ");

        if (value.length() < 2 || value.length() > 150) {
            throw new IllegalArgumentException(
                    "Full name must contain 2 to 150 characters"
            );
        }
    }

    public static FullName of(String value) {
        return new FullName(value);
    }
}
