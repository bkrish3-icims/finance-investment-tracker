# DB Schema Design (Per Service)

## Global Conventions (Use the same conventions in all services)
- PK type: UUID (in Java: UUID, in DB: uuid).
- Timestamps: created_at, updated_at (timestamptz).
- User owner fields: user_id uuid (not FK across DB, but logically tied).
- Soft delete (optional): is_deleted boolean default false.
- Optimistic locking: version bigint if you want.