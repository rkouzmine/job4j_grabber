CREATE TABLE post (
    id SERIAL PRIMARY KEY,
    title VARCHAR(100),
    link TEXT UNIQUE,
    description TEXT,
    time TIMESTAMP WITHOUT TIME ZONE
);