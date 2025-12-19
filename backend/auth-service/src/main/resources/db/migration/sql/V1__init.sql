CREATE TABLE users (
    id                   uuid PRIMARY KEY,
    username                varchar(255) NOT NULL UNIQUE,
    email                varchar(255) NOT NULL UNIQUE,
    full_name            varchar(255) NOT NULL,
    is_active            boolean NOT NULL DEFAULT true,
    is_admin             boolean NOT NULL DEFAULT false,
    previous_passwords   varchar(255),
    created_at           timestamptz NOT NULL DEFAULT now(),
    updated_at           timestamptz NOT NULL DEFAULT now()
);

CREATE INDEX idx_users_email ON users (email);

CREATE TABLE tokens (
    id          uuid PRIMARY KEY,
    user_id     uuid NOT NULL,
    token_hash  varchar(255) NOT NULL,
    is_active   boolean NOT NULL DEFAULT true,
    expires_at  timestamptz NOT NULL DEFAULT now(),
    revoked     boolean NOT NULL DEFAULT false,
    used        boolean NOT NULL DEFAULT false,
    updated_at  timestamptz NOT NULL DEFAULT now(),
    CONSTRAINT fk_tokens_user
        FOREIGN KEY (user_id)
            REFERENCES users (id)
            ON DELETE CASCADE
);
