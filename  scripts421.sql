ALTER TABLE student add constraint age_constraint check (age > 16);

ALTER TABLE student add constraint name_constraint unique (name);

ALTER TABLE student add constraint name_constraint1 check ( name IS NOT NULL);

alter table student alter column age set default 20;

alter table faculty add constraint name_constr unique (name,color);