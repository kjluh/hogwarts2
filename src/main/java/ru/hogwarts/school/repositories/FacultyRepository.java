package ru.hogwarts.school.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

public interface FacultyRepository extends JpaRepository<Faculty,Long> {

    Faculty findByNameIgnoreCaseOrColorIgnoreCase(String name, String color);

    Faculty findFacultyByNameIgnoreCase(String name);

}
