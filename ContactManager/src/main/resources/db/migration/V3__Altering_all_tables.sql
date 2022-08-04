ALTER TABLE contacts ALTER COLUMN email DROP NOT NULL;
ALTER TABLE contacts ALTER COLUMN created_at SET NOT NULL;
ALTER TABLE contacts ALTER COLUMN updated_at SET NOT NULL;
ALTER TABLE contacts ADD COLUMN uid UUID NOT NULL UNIQUE DEFAULT (gen_random_uuid());

ALTER TABLE users ALTER COLUMN created_at SET NOT NULL;
ALTER TABLE users ALTER COLUMN updated_at SET NOT NULL;
ALTER TABLE users ADD CONSTRAINT users_unique_email UNIQUE (email);
ALTER TABLE users ADD COLUMN uid UUID NOT NULL UNIQUE DEFAULT (gen_random_uuid());
ALTER TABLE users ADD UNIQUE (email);

ALTER TABLE contact_types ADD UNIQUE (contact_type_name);
ALTER TABLE contact_types ADD COLUMN uid UUID NOT NULL UNIQUE DEFAULT (gen_random_uuid());