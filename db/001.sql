CREATE TABLE if not exists cities
(
    city varchar(30) unique
);
insert into cities (city)
values ('Moscow'),
       ('Kiev'),
       ('Delly'),
       ('London'),
       ('Boston'),
       ('Tokyo');