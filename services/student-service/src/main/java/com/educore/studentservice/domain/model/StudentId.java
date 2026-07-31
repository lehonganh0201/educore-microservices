package com.educore.studentservice.domain.model;

import java.util.Objects;
import java.util.UUID;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    22/07/2026 at 15:08
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

public record StudentId(UUID value) {

    public StudentId {
        Objects.requireNonNull(
                value,
                "Student ID must not be null"
        );
    }

    public static StudentId generate() {
        return new StudentId(UUID.randomUUID());
    }

    public static StudentId of(UUID value) {
        return new StudentId(value);
    }

    public static StudentId from(String value) {
        return new StudentId(UUID.fromString(value));
    }
}
