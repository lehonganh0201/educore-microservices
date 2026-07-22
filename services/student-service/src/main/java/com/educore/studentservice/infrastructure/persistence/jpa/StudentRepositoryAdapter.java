package com.educore.studentservice.infrastructure.persistence.jpa;

import com.educore.studentservice.application.port.out.StudentRepositoryPort;
import com.educore.studentservice.domain.exception.StudentAlreadyExistsException;
import com.educore.studentservice.domain.exception.StudentConcurrentModificationException;
import com.educore.studentservice.domain.model.IdentityId;
import com.educore.studentservice.domain.model.Student;
import com.educore.studentservice.domain.model.StudentCode;
import com.educore.studentservice.domain.model.StudentId;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    22/07/2026 at 16:00
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@Component
public class StudentRepositoryAdapter implements StudentRepositoryPort {

    private final SpringDataStudentRepository repository;
    private final StudentPersistenceMapper mapper;

    public StudentRepositoryAdapter(SpringDataStudentRepository repository, StudentPersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public boolean existsByIdentityId(IdentityId identityId) {
        return repository.existsByIdentityId(
                identityId.value()
        );
    }

    @Override
    public boolean existsByStudentCode(StudentCode studentCode) {
        return repository.existsByStudentCode(
                studentCode.value()
        );
    }

    @Override
    public Optional<Student> findById(StudentId studentId) {
        return repository
                .findById(studentId.value())
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Student> findByIdentityId(
            IdentityId identityId
    ) {
        return repository
                .findByIdentityId(identityId.value())
                .map(mapper::toDomain);
    }

    @Override
    public Student save(Student student) {
        try {
            StudentJpaEntity entity = repository
                    .findById(student.id().value())
                    .map(existing -> {
                        mapper.copyToEntity(
                                student,
                                existing
                        );
                        return existing;
                    })
                    .orElseGet(() ->
                            mapper.toNewEntity(student)
                    );

            return mapper.toDomain(
                    repository.saveAndFlush(entity)
            );

        } catch (ObjectOptimisticLockingFailureException exception) {
            throw new StudentConcurrentModificationException("Student concurrent modification: " + exception.getMessage());

        } catch (DataIntegrityViolationException exception) {
            throw new StudentAlreadyExistsException("Identity or Student Code already exists");
        }
    }
}