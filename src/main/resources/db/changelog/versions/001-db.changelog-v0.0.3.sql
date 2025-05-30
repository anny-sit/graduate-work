-- liquibase formatted sql
-- changeset anny_sit:2
ALTER TABLE comments
DROP CONSTRAINT IF EXISTS fk_comments_ad_id;

-- changeset anny_sit:3
ALTER TABLE comments
ADD CONSTRAINT fk_comments_ad_id
FOREIGN KEY (ad_id)
REFERENCES ad_table (pk)
ON DELETE CASCADE;