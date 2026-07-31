package com.educore.studentservice.domain.exception;

import com.educore.web.exception.BadRequestException;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    22/07/2026 at 15:13
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public class UnauthenticatedIdentityException extends BadRequestException {
    public UnauthenticatedIdentityException(String message) {
        super(message);
    }
}
