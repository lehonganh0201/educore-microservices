package com.educore.studentservice.application.service;

import com.educore.common.dto.PageResponse;
import com.educore.data.pagination.SpringPageResponseMapper;
import com.educore.studentservice.application.command.ChangeStudentStatusCommand;
import com.educore.studentservice.application.command.CreateStudentCommand;
import com.educore.studentservice.application.command.UpdateStudentCommand;
import com.educore.studentservice.application.port.in.StudentManagementUseCase;
import com.educore.studentservice.application.port.out.StudentRepositoryPort;
import com.educore.studentservice.application.port.out.model.StudentSearchCriteria;
import com.educore.studentservice.application.query.SearchStudentsQuery;
import com.educore.studentservice.application.result.StudentResult;
import com.educore.studentservice.domain.exception.StudentAlreadyExistsException;
import com.educore.studentservice.domain.exception.StudentNotFoundException;
import com.educore.studentservice.domain.model.*;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    22/07/2026 at 15:41
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@Service
public class StudentApplicationService implements StudentManagementUseCase {
    private final StudentRepositoryPort studentRepository;
    private final Clock clock;

    public StudentApplicationService(StudentRepositoryPort studentRepository, Clock clock) {
        this.studentRepository = studentRepository;
        this.clock = clock;
    }

    @Override
    public StudentResult create(CreateStudentCommand command) {
        IdentityId identityId = IdentityId.of(command.identityId());

        StudentCode studentCode = StudentCode.of(command.studentCode());

        if (studentRepository.existsByIdentityId(identityId)) {
            throw new StudentAlreadyExistsException("Identity already exists " + identityId);
        }

        if (studentRepository.existsByStudentCode(studentCode)) {
            throw new StudentAlreadyExistsException("Student code already exists " + studentCode);
        }

        Instant now = clock.instant();

        Student student = Student.create(
                identityId,
                studentCode,
                FullName.of(command.fullName()),
                command.dateOfBirth(),
                command.gender(),
                command.phone(),
                command.address(),
                command.enrollmentYear(),
                LocalDate.now(clock),
                now
        );

        return StudentResult.from(studentRepository.save(student));
    }

    @Override
    @Transactional(readOnly = true)
    public StudentResult findById(UUID studentId) {
        return StudentResult.from(getStudent(StudentId.of(studentId)));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<StudentResult> search(SearchStudentsQuery query) {
        Page<StudentResult> resultPage = studentRepository.search(
                new StudentSearchCriteria(
                        query.keyword(),
                        query.status(),
                        query.gender(),
                        query.enrollmentYear(),
                        query.page(),
                        query.size()
                )
        ).map(StudentResult::from);

        return SpringPageResponseMapper.from(resultPage);
    }

    @Override
    public StudentResult update(
            UpdateStudentCommand command
    ) {
        Student current = getStudent(
                StudentId.of(command.studentId())
        );

        Student updated = current.updateAdministrativeProfile(
                        FullName.of(command.fullName()),
                        command.dateOfBirth(),
                        command.gender(),
                        command.phone(),
                        command.address(),
                        command.enrollmentYear(),
                        LocalDate.now(clock),
                        clock.instant()
                );

        return StudentResult.from(
                studentRepository.save(updated)
        );
    }

    @Override
    public StudentResult changeStatus(
            ChangeStudentStatusCommand command
    ) {
        Student current = getStudent(
                StudentId.of(command.studentId())
        );

        Student updated = current.changeStatus(
                command.status(),
                clock.instant()
        );

        return StudentResult.from(
                studentRepository.save(updated)
        );
    }

    private Student getStudent(StudentId studentId) {
        return studentRepository
                .findById(studentId)
                .orElseThrow(() ->
                        new StudentNotFoundException("Not found student id " + studentId.value())
                );
    }
}
