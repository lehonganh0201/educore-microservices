-- EduCore - Initial databases
-- Các database sẽ thuộc sở hữu của POSTGRES_USER: lehonganh

SELECT 'CREATE DATABASE keycloak'
    WHERE NOT EXISTS (
    SELECT FROM pg_database WHERE datname = 'keycloak'
)\gexec

SELECT 'CREATE DATABASE student_db'
    WHERE NOT EXISTS (
    SELECT FROM pg_database WHERE datname = 'student_db'
)\gexec