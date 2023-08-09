create table students
(
    id   serial primary key ,
    name varchar unique not null,
    age  integer check ( age >= 16 ) default (20),
    faculty_id integer references faculty(id)
);

create table faculty
(
    id serial primary key ,
    name varchar not null ,
    color varchar not null,
    unique (name, color)

)

