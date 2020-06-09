CREATE TABLE if not exists post
(
    id   SERIAL PRIMARY KEY,
    name TEXT
);

CREATE TABLE if not exists candidate
(
    id      SERIAL PRIMARY KEY,
    name    TEXT,
    photoId text
);

create table if not exists users
(
    id       serial primary key,
    name     text unique,
    email    text,
    password text
);
insert into users (name, email, password) VALUES ('admin', 'admin', 'admin');