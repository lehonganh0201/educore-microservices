CREATE TABLE students
(
    id              UUID         NOT NULL,
    identity_id     VARCHAR(100) NOT NULL,
    student_code    VARCHAR(30)  NOT NULL,
    full_name       VARCHAR(150) NOT NULL,
    date_of_birth   DATE         NOT NULL,
    gender          VARCHAR(20)  NOT NULL,
    phone           VARCHAR(30),
    address         VARCHAR(500),
    enrollment_year INTEGER      NOT NULL,
    status          VARCHAR(30)  NOT NULL,
    version         BIGINT       NOT NULL DEFAULT 0,
    created_at      TIMESTAMPTZ  NOT NULL,
    updated_at      TIMESTAMPTZ  NOT NULL,

    CONSTRAINT pk_students
        PRIMARY KEY (id),

    CONSTRAINT uk_students_identity_id
        UNIQUE (identity_id),

    CONSTRAINT uk_students_student_code
        UNIQUE (student_code),

    CONSTRAINT chk_students_gender
        CHECK (
            gender IN (
                       'MALE',
                       'FEMALE',
                       'OTHER',
                       'UNSPECIFIED'
                )
            ),

    CONSTRAINT chk_students_status
        CHECK (
            status IN (
                       'PENDING',
                       'ACTIVE',
                       'SUSPENDED',
                       'GRADUATED',
                       'WITHDRAWN'
                )
            ),

    CONSTRAINT chk_students_enrollment_year
        CHECK (
            enrollment_year BETWEEN 2000 AND 2100
            )
);

CREATE INDEX idx_students_status
    ON students (status);

CREATE INDEX idx_students_enrollment_year
    ON students (enrollment_year);

CREATE INDEX idx_students_full_name
    ON students (full_name);

CREATE INDEX idx_students_created_at
    ON students (created_at DESC);