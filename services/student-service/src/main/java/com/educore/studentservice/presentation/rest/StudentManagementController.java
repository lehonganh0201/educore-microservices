package com.educore.studentservice.presentation.rest;

import com.educore.common.dto.ApiResponse;
import com.educore.common.dto.PageResponse;
import com.educore.studentservice.application.command.ChangeStudentStatusCommand;
import com.educore.studentservice.application.command.CreateStudentCommand;
import com.educore.studentservice.application.command.UpdateStudentCommand;
import com.educore.studentservice.application.port.in.StudentManagementUseCase;
import com.educore.studentservice.application.query.SearchStudentsQuery;
import com.educore.studentservice.application.result.StudentResult;
import com.educore.studentservice.domain.model.Gender;
import com.educore.studentservice.domain.model.StudentStatus;
import com.educore.studentservice.presentation.rest.request.ChangeStudentStatusRequest;
import com.educore.studentservice.presentation.rest.request.CreateStudentRequest;
import com.educore.studentservice.presentation.rest.request.UpdateStudentRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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

    @GetMapping
    @PreAuthorize(
            "hasAnyRole('ADMIN', 'LECTURER')"
    )
    public ResponseEntity<PageResponse<StudentResult>> search(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(required = false) StudentStatus status,
            @RequestParam(required = false) Gender gender,
            @RequestParam(required = false) Integer enrollmentYear,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "20") @Min(1) @Max(100) int size
    ) {
        return ResponseEntity.ok(studentManagementUseCase.search(
                new SearchStudentsQuery(
                        keyword,
                        status,
                        gender,
                        enrollmentYear,
                        page,
                        size
                ))
        );
    }

    @GetMapping("/{studentId}")
    @PreAuthorize(
            "hasAnyRole('ADMIN', 'LECTURER')"
    )
    public ResponseEntity<ApiResponse<StudentResult>> findById(@PathVariable UUID studentId) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Get student by id successful",
                        studentManagementUseCase
                                .findById(studentId)
                )
        );
    }

    @PutMapping("/{studentId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<StudentResult>> update(
            @PathVariable UUID studentId,
            @Valid @RequestBody UpdateStudentRequest request
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Update student successful",
                        studentManagementUseCase.update(
                                new UpdateStudentCommand(
                                        studentId,
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

    @PatchMapping("/{studentId}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<StudentResult>> changeStatus(
            @PathVariable UUID studentId,
            @Valid @RequestBody ChangeStudentStatusRequest request
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Change student status successful",
                        studentManagementUseCase.changeStatus(
                                        new ChangeStudentStatusCommand(
                                                studentId,
                                                request.status()
                                        )
                                )
                )
        );
    }
}
