package com.educore.studentservice.presentation.rest;

import com.educore.common.dto.ApiResponse;
import com.educore.studentservice.application.command.UpdateMyStudentProfileCommand;
import com.educore.studentservice.application.port.in.StudentSelfServiceUseCase;
import com.educore.studentservice.application.result.StudentResult;
import com.educore.studentservice.presentation.rest.request.UpdateMyStudentProfileRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
                        "Get self profile successful",
                        studentSelfServiceUseCase.getMyProfile()
                )
        );
    }

    @PatchMapping
    public ResponseEntity<ApiResponse<StudentResult>> updateMyProfile(
            @Valid @RequestBody UpdateMyStudentProfileRequest request
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Update self profile successful",
                        studentSelfServiceUseCase.updateMyProfile(
                                        new UpdateMyStudentProfileCommand(
                                                request.phone(),
                                                request.address()
                                        )
                                )
                )
        );
    }
}
