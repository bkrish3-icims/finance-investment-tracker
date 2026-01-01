-- Enable UUID extension if not already enabled
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Add DEFAULT value to existing id column
ALTER TABLE users
    ALTER COLUMN id SET DEFAULT uuid_generate_v4();