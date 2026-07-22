package com.educore.studentservice.infrastructure.persistence.jpa;

import com.educore.studentservice.domain.model.*;
import org.springframework.stereotype.Component;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    22/07/2026 at 15:58
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@Component
public class StudentPersistenceMapper {

    public Student toDomain(StudentJpaEntity entity) {
        return Student.rehydrate(
                StudentId.of(entity.getId()),
                IdentityId.of(entity.getIdentityId()),
                StudentCode.of(entity.getStudentCode()),
                FullName.of(entity.getFullName()),
                entity.getDateOfBirth(),
                entity.getGender(),
                entity.getPhone(),
                entity.getAddress(),
                entity.getEnrollmentYear(),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public StudentJpaEntity toNewEntity(Student student) {
        StudentJpaEntity entity = new StudentJpaEntity();

        copyToEntity(student, entity);

        return entity;
    }

    public void copyToEntity(Student student, StudentJpaEntity entity) {
        entity.setId(student.id().value());
        entity.setIdentityId(
                student.identityId().value()
        );
        entity.setStudentCode(
                student.studentCode().value()
        );
        entity.setFullName(
                student.fullName().value()
        );
        entity.setDateOfBirth(
                student.dateOfBirth()
        );
        entity.setGender(student.gender());
        entity.setPhone(student.phone());
        entity.setAddress(student.address());
        entity.setEnrollmentYear(
                student.enrollmentYear()
        );
        entity.setStatus(student.status());
        entity.setCreatedAt(student.createdAt());
        entity.setUpdatedAt(student.updatedAt());
    }
}
