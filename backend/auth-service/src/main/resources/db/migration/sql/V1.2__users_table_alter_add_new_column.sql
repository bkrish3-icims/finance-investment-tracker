-- V1.2__add_password_hash.sql
ALTER TABLE users ADD COLUMN password_hash varchar(255);

-- Populate from your existing password_hash transient field logic
-- Option A: If you have existing hashes to migrate
-- UPDATE users SET password_hash = some_hash_function();

-- Option B: Set empty/default for now (update via app later)
UPDATE users SET password_hash = '';

ALTER TABLE users ALTER COLUMN password_hash SET NOT NULL;