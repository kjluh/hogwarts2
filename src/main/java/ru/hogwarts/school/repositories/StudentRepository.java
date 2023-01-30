package ru.hogwarts.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student,Long> {

    Collection<Student> findByAgeBetween(int max, int min);

    Student findStudentById(Long id);
    @Query(value = "select * from student order by id desc Limit 5",nativeQuery = true)
    List<Student> get5Students();

}
