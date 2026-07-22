package com.educore.identifyservice.presentation.rest.request;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    22/07/2026 at 10:18
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

public record ChangeAccountStatusRequest(
        boolean enabled
) {
}
