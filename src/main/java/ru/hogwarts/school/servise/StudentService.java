package ru.hogwarts.school.servise;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Collection;
import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    Logger logger = LoggerFactory.getLogger(StudentService.class);

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student getStudent(Long id) {
        logger.debug("Вызван метод getStudent c id {}",id);
        return studentRepository.findById(id).orElse(null);
    }

    public List<Student> getStudentByAge(int age) {
        logger.debug("Вызван метод getStudentByAge c возрастом {}",age);
        return studentRepository.findAll().stream().filter(e -> e.getAge() == age).toList();
    }

    public Student createStudent(Student student) {
        logger.debug("Вызван метод createStudent");
        return studentRepository.save(student);
    }

    public Student updateStudent(Student student) {
        logger.debug("Вызван метод updateStudent");
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        logger.debug("Вызван метод deleteStudent c id {}",id);
        studentRepository.deleteById(id);
    }

    public Collection<Student> findAllByAgeBetween(int max, int min) {
        logger.debug("Вызван метод findAllByAgeBetween ");
        return studentRepository.findByAgeBetween(max, min);
    }

    public String findByName(Long id) {
        logger.debug("Вызван метод findByName ");
        Student s = studentRepository.findStudentById(id);
        if (s == null) {
            return null;
        }
        return s.getFaculty();
    }

    public Collection<Student> getFiveStudents() {
        logger.debug("Вызван метод getFiveStudents ");
        return studentRepository.get5Students();
    }
    public String getAllStudents() {
        logger.debug("Вызван метод getAllStudents ");
        String count = " " + studentRepository.getAllStudents() + " ";
        return "количество студентов в школе: " + count;
    }

    public String getAvgAge() {
        logger.debug("Вызван метод getAvgAge ");
        String age = " " + studentRepository.getAvgAge() + " ";
        return "средний возраст студентов в школе: " + age;
    }
}
