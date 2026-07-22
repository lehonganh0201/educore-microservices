package com.educore.studentservice.application.port.in;

import com.educore.common.dto.PageResponse;
import com.educore.studentservice.application.command.CreateStudentCommand;
import com.educore.studentservice.application.command.UpdateStudentCommand;
import com.educore.studentservice.application.query.SearchStudentsQuery;
import com.educore.studentservice.application.result.StudentResult;

import java.util.UUID;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    22/07/2026 at 15:39
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public interface StudentManagementUseCase {
    StudentResult create(CreateStudentCommand command);

    StudentResult findById(UUID studentId);

    PageResponse<StudentResult> search(SearchStudentsQuery query);

    StudentResult update(UpdateStudentCommand command);
}
