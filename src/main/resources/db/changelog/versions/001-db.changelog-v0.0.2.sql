-- liquibase formatted sql
-- changeset anny_sit:1
create index user_email_password_index on user_table (email, password)