package com.educore.studentservice.domain.exception;

import com.educore.web.exception.NotFoundException;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    22/07/2026 at 15:11
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public class StudentNotFoundException extends NotFoundException {
    public StudentNotFoundException(String message) {
        super(message);
    }
}
