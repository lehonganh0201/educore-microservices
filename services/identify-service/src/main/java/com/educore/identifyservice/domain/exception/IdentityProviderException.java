package com.educore.identifyservice.domain.exception;

import com.educore.web.exception.BadRequestException;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    21/07/2026 at 11:25
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public class IdentityProviderException extends BadRequestException {
    public IdentityProviderException(String message) {
        super(message);
    }
}
