package com.educore.identifyservice.domain.model;

import java.util.Objects;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    21/07/2026 at 10:59
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

public record RawPassword(String value) {

    public RawPassword {
        Objects.requireNonNull(value, "Password must not be null");

        if (value.length() < 8 || value.length() > 100) {
            throw new IllegalArgumentException(
                    "Password must contain 8 to 100 characters"
            );
        }
    }

    @Override
    public String toString() {
        return "[PROTECTED]";
    }
}
