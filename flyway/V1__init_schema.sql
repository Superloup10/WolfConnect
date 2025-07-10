CREATE TABLE IF NOT EXISTS users(
    id uuid PRIMARY KEY,
    email TEXT NOT NULL UNIQUE,
    password_hash TEXT NOT NULL,
    role TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS messages(
    id uuid PRIMARY KEY,
    sender_id uuid REFERENCES users(id),
    to_user_id uuid REFERENCES users(id),
    content TEXT NOT NULL,
    datetime TIMESTAMP DEFAULT NOW(),
    is_anonymous BOOLEAN DEFAULT FALSE,
    visible_sender_name TEXT,
    status TEXT DEFAULT 'pending'
);
