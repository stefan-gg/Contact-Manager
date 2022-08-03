ALTER TABLE users
    ADD COLUMN updated_at TIMESTAMP,
    ADD COLUMN created_at TIMESTAMP;

ALTER TABLE contacts
    ADD COLUMN updated_at TIMESTAMP,
    ADD COLUMN created_at TIMESTAMP;