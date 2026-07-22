package com.educore.studentservice.presentation.rest;

import com.educore.common.dto.ApiResponse;
import com.educore.studentservice.application.port.in.StudentSelfServiceUseCase;
import com.educore.studentservice.application.result.StudentResult;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    22/07/2026 at 17:33
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@RestController
@RequestMapping("/api/v1/students/me")
@PreAuthorize("hasRole('STUDENT')")
public class StudentSelfController {
    private final StudentSelfServiceUseCase studentSelfServiceUseCase;

    public StudentSelfController(StudentSelfServiceUseCase studentSelfServiceUseCase) {
        this.studentSelfServiceUseCase = studentSelfServiceUseCase;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<StudentResult>> getMyProfile() {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "GEt self profile successful",
                        studentSelfServiceUseCase.getMyProfile()
                )
        );
    }
}
