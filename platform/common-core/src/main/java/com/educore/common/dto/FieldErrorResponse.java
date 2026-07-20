package com.educore.common.dto;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    19/07/2026 at 19:27
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

public record FieldErrorResponse(
        String field,
        String message
) {
}