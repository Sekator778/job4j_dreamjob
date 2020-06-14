CREATE TABLE if not exists cities
(
    id   serial primary key,
    name varchar(30) unique
);

CREATE TABLE if not exists post
(
    id   SERIAL PRIMARY KEY,
    name TEXT
);

CREATE TABLE if not exists candidate
(
    id      SERIAL PRIMARY KEY,
    name    TEXT,
    photoId text,
    cityId  int,
    foreign key (cityId) references cities(id)
);

create table if not exists users
(
    id       serial primary key,
    name     text unique,
    email    text,
    password text
);



insert into cities (name)
values ('Moscow'),
       ('Kiev'),
       ('Delly'),
       ('London'),
       ('Boston'),
       ('Tokyo');