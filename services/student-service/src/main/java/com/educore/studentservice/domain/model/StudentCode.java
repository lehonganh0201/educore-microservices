package com.educore.studentservice.domain.model;

import java.util.Locale;
import java.util.Objects;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    22/07/2026 at 15:09
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

public record StudentCode(String value) {

    private static final String PATTERN =
            "^[A-Z0-9][A-Z0-9_-]{2,29}$";

    public StudentCode {
        Objects.requireNonNull(
                value,
                "Student code must not be null"
        );

        value = value.trim()
                .toUpperCase(Locale.ROOT);

        if (!value.matches(PATTERN)) {
            throw new IllegalArgumentException(
                    "Student code must contain 3 to 30 "
                            + "uppercase letters, numbers, "
                            + "hyphens or underscores"
            );
        }
    }

    public static StudentCode of(String value) {
        return new StudentCode(value);
    }
}