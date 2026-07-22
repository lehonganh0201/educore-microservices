package com.educore.studentservice.domain.model;

import com.educore.studentservice.domain.exception.InvalidStudentStatusTransitionException;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

/**
 * ----------------------------------------------------------------------------
 * Author:        Hong Anh
 * Created on:    22/07/2026 at 15:08
 * Project:       educore-microservices
 * Contact:       https://github.com/lehonganh0201
 * ----------------------------------------------------------------------------
 */


public final class Student {

    private static final int MIN_ENROLLMENT_YEAR = 2000;
    private static final int MAX_ENROLLMENT_YEAR = 2100;

    private final StudentId id;
    private final IdentityId identityId;
    private final StudentCode studentCode;
    private final FullName fullName;
    private final LocalDate dateOfBirth;
    private final Gender gender;
    private final String phone;
    private final String address;
    private final int enrollmentYear;
    private final StudentStatus status;
    private final Instant createdAt;
    private final Instant updatedAt;

    private Student(
            StudentId id,
            IdentityId identityId,
            StudentCode studentCode,
            FullName fullName,
            LocalDate dateOfBirth,
            Gender gender,
            String phone,
            String address,
            int enrollmentYear,
            StudentStatus status,
            Instant createdAt,
            Instant updatedAt
    ) {
        this.id = Objects.requireNonNull(id);
        this.identityId = Objects.requireNonNull(identityId);
        this.studentCode = Objects.requireNonNull(studentCode);
        this.fullName = Objects.requireNonNull(fullName);
        this.dateOfBirth = Objects.requireNonNull(dateOfBirth);
        this.gender = Objects.requireNonNull(gender);
        this.phone = normalizePhone(phone);
        this.address = normalizeAddress(address);
        this.enrollmentYear = validateEnrollmentYear(
                enrollmentYear
        );
        this.status = Objects.requireNonNull(status);
        this.createdAt = Objects.requireNonNull(createdAt);
        this.updatedAt = Objects.requireNonNull(updatedAt);
    }

    public static Student create(
            IdentityId identityId,
            StudentCode studentCode,
            FullName fullName,
            LocalDate dateOfBirth,
            Gender gender,
            String phone,
            String address,
            int enrollmentYear,
            LocalDate currentDate,
            Instant now
    ) {
        validateDateOfBirth(dateOfBirth, currentDate);

        return new Student(
                StudentId.generate(),
                identityId,
                studentCode,
                fullName,
                dateOfBirth,
                gender,
                phone,
                address,
                enrollmentYear,
                StudentStatus.ACTIVE,
                now,
                now
        );
    }

    public static Student rehydrate(
            StudentId id,
            IdentityId identityId,
            StudentCode studentCode,
            FullName fullName,
            LocalDate dateOfBirth,
            Gender gender,
            String phone,
            String address,
            int enrollmentYear,
            StudentStatus status,
            Instant createdAt,
            Instant updatedAt
    ) {
        return new Student(
                id,
                identityId,
                studentCode,
                fullName,
                dateOfBirth,
                gender,
                phone,
                address,
                enrollmentYear,
                status,
                createdAt,
                updatedAt
        );
    }

    public Student updateAdministrativeProfile(
            FullName fullName,
            LocalDate dateOfBirth,
            Gender gender,
            String phone,
            String address,
            int enrollmentYear,
            LocalDate currentDate,
            Instant now
    ) {
        validateDateOfBirth(dateOfBirth, currentDate);

        return new Student(
                id,
                identityId,
                studentCode,
                fullName,
                dateOfBirth,
                gender,
                phone,
                address,
                enrollmentYear,
                status,
                createdAt,
                now
        );
    }

    public Student updateOwnContact(
            String phone,
            String address,
            Instant now
    ) {
        return new Student(
                id,
                identityId,
                studentCode,
                fullName,
                dateOfBirth,
                gender,
                phone,
                address,
                enrollmentYear,
                status,
                createdAt,
                now
        );
    }

    public Student changeStatus(
            StudentStatus targetStatus,
            Instant now
    ) {
        if (!status.canTransitionTo(targetStatus)) {
            throw new InvalidStudentStatusTransitionException("Invalid student status transition.");
        }

        if (status == targetStatus) {
            return this;
        }

        return new Student(
                id,
                identityId,
                studentCode,
                fullName,
                dateOfBirth,
                gender,
                phone,
                address,
                enrollmentYear,
                targetStatus,
                createdAt,
                now
        );
    }

    private static void validateDateOfBirth(
            LocalDate dateOfBirth,
            LocalDate currentDate
    ) {
        Objects.requireNonNull(
                dateOfBirth,
                "Date of birth must not be null"
        );

        Objects.requireNonNull(
                currentDate,
                "Current date must not be null"
        );

        if (!dateOfBirth.isBefore(currentDate)) {
            throw new IllegalArgumentException(
                    "Date of birth must be before current date"
            );
        }
    }

    private static int validateEnrollmentYear(int year) {
        if (year < MIN_ENROLLMENT_YEAR || year > MAX_ENROLLMENT_YEAR) {
            throw new IllegalArgumentException(
                    "Enrollment year must be between "
                            + MIN_ENROLLMENT_YEAR
                            + " and "
                            + MAX_ENROLLMENT_YEAR
            );
        }

        return year;
    }

    private static String normalizePhone(String phone) {
        if (phone == null || phone.isBlank()) {
            return null;
        }

        String normalized = phone.trim();

        if (!normalized.matches("^[+]?[0-9][0-9 .-]{7,19}$")) {
            throw new IllegalArgumentException("Phone number is invalid");
        }

        return normalized;
    }

    private static String normalizeAddress(String address) {
        if (address == null || address.isBlank()) {
            return null;
        }

        String normalized = address.trim().replaceAll("\\s+", " ");

        if (normalized.length() > 500) {
            throw new IllegalArgumentException("Address must not exceed 500 characters");
        }

        return normalized;
    }

    public StudentId id() {
        return id;
    }

    public IdentityId identityId() {
        return identityId;
    }

    public StudentCode studentCode() {
        return studentCode;
    }

    public FullName fullName() {
        return fullName;
    }

    public LocalDate dateOfBirth() {
        return dateOfBirth;
    }

    public Gender gender() {
        return gender;
    }

    public String phone() {
        return phone;
    }

    public String address() {
        return address;
    }

    public int enrollmentYear() {
        return enrollmentYear;
    }

    public StudentStatus status() {
        return status;
    }

    public Instant createdAt() {
        return createdAt;
    }

    public Instant updatedAt() {
        return updatedAt;
    }
}
