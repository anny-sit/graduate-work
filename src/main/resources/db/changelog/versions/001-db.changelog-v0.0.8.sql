-- liquibase formatted sql
-- changeset anny_sit:8

ALTER TABLE users ALTER COLUMN password TYPE VARCHAR(1000);