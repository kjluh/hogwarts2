--liquibase formatted sql

-- changeSet plahovA: 1
create index student_name on student (name);
create index faculty_info on faculty (name, color);

-- changeSet plahovA: 2
drop index student_name;