/*create table students
(
    id   serial primary key ,
    name varchar unique not null,
    age  integer check ( age >= 16 ) default (20),
    faculty_id integer references faculty(id)
);*/

/*create table faculty
(
    id serial primary key ,
    name varchar not null ,
    color varchar not null,
    unique (name, color)

)*/

alter table students
    add constraint age_constraint check ( age >= 16 );

alter table students
    add constraint name_constraint check ( not null);

alter table students
    add constraint name_unique unique (name);

alter table faculty
add constraint unique_combination unique (name, color);


alter table students
    alter column age set default 20;
