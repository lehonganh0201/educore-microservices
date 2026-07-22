package com.educore.studentservice.presentation.rest;

import com.educore.common.dto.ApiResponse;
import com.educore.studentservice.application.command.CreateStudentCommand;
import com.educore.studentservice.application.port.in.StudentManagementUseCase;
import com.educore.studentservice.application.result.StudentResult;
import com.educore.studentservice.presentation.rest.request.CreateStudentRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    22/07/2026 at 16:11
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@RestController
@RequestMapping("/api/v1/students")
@Validated
public class StudentManagementController {
    private final StudentManagementUseCase studentManagementUseCase;

    public StudentManagementController(StudentManagementUseCase studentManagementUseCase) {
        this.studentManagementUseCase = studentManagementUseCase;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<StudentResult>> create(
            @Valid @RequestBody CreateStudentRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.success(
                                "Create student successful",
                                studentManagementUseCase.create(
                                        new CreateStudentCommand(
                                                request.identityId(),
                                                request.studentCode(),
                                                request.fullName(),
                                                request.dateOfBirth(),
                                                request.gender(),
                                                request.phone(),
                                                request.address(),
                                                request.enrollmentYear()
                                        )
                                )
                        )
                );
    }
}
