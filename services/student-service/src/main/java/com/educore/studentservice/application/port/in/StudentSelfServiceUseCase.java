package com.educore.studentservice.application.port.in;

import com.educore.studentservice.application.result.StudentResult;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    22/07/2026 at 17:28
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public interface StudentSelfServiceUseCase {
    StudentResult getMyProfile();
}
