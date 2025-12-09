CREATE DATABASE auth_db;
CREATE DATABASE finance_db;

-- User for the first database
CREATE USER postgres WITH PASSWORD 'postgres123';
GRANT ALL PRIVILEGES ON DATABASE auth_db TO postgres;
GRANT ALL PRIVILEGES ON DATABASE finance_db TO postgres;

\connect auth_db;
ALTER SCHEMA public OWNER TO postgres;

\connect finance_db;
ALTER SCHEMA public OWNER TO postgres;


-- You might want a shared user or more granular permissions
