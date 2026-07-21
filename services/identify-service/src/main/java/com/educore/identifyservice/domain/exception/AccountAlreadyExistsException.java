package com.educore.identifyservice.domain.exception;

import com.educore.web.exception.ConflictException;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    21/07/2026 at 11:23
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public class AccountAlreadyExistsException extends ConflictException {
    public AccountAlreadyExistsException(String message) {
        super(message);
    }
}
