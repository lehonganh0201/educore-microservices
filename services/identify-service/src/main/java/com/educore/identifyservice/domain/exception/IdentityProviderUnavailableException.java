package com.educore.identifyservice.domain.exception;

import com.educore.web.exception.ServiceUnavailableException;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    21/07/2026 at 11:26
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public class IdentityProviderUnavailableException extends ServiceUnavailableException {
    public IdentityProviderUnavailableException(String message) {
        super(message);
    }
}
