package com.educore.studentservice.application.port.out;

import com.educore.studentservice.application.port.out.model.StudentSearchCriteria;
import com.educore.studentservice.domain.model.IdentityId;
import com.educore.studentservice.domain.model.Student;
import com.educore.studentservice.domain.model.StudentCode;
import com.educore.studentservice.domain.model.StudentId;
import org.springframework.data.domain.Page;

import java.util.Optional;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    22/07/2026 at 15:35
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public interface StudentRepositoryPort {
    boolean existsByIdentityId(IdentityId identityId);

    boolean existsByStudentCode(StudentCode studentCode);

    Optional<Student> findById(StudentId studentId);

    Optional<Student> findByIdentityId(
            IdentityId identityId
    );

    Student save(Student student);

    Page<Student> search(StudentSearchCriteria studentSearchCriteria);
}
