package com.educore.identifyservice.domain.exception;

import com.educore.web.exception.BadRequestException;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    21/07/2026 at 11:30
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public class IdentityProviderPermissionException extends BadRequestException {
    public IdentityProviderPermissionException(String message) {
        super(message);
    }
}
