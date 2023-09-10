create table persons
(
    id      serial primary key,
    name    varchar not null,
    age     integer check ( age > 0 ),
    unique (name, age),
    license boolean,
    car_id  integer references cars(id)
);

create table cars
(
    id    serial primary key,
    brand varchar not null,
    model varchar not null,
    cost  integer not null
);