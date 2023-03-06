create table people
(
    id             integer primary key,
    name           varchar not null,
    age            integer default 1,
    driver_license boolean default 0,
    car integer[] references auto (id)
);
create table auto
(
    id integer primary key,
    mark           varchar not null,
    model           varchar default 'supra',
    cost        integer not null ,
    driver integer references  people (id)
);

select student.name, student.age, faculty.name
from student
         INNER JOIN faculty on student.faculty_id = faculty.id;

select student.name
from student
         INNER JOIN avatar on student.id = avatar.student_id;