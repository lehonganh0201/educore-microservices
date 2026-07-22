package com.educore.studentservice.infrastructure.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    22/07/2026 at 15:55
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */

@Repository
public interface SpringDataStudentRepository extends JpaRepository<StudentJpaEntity, UUID>, JpaSpecificationExecutor<StudentJpaEntity> {

    boolean existsByIdentityId(String identityId);

    boolean existsByStudentCode(String studentCode);

    Optional<StudentJpaEntity> findByIdentityId(String identityId);
}