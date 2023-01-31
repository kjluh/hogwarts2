package ru.hogwarts.school.servise;

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

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student getStudent(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    public List<Student> getStudentByAge(int age) {
        return studentRepository.findAll().stream().filter(e -> e.getAge() == age).toList();
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student updateStudent(Student student) {
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public Collection<Student> findAllByAgeBetween(int max, int min) {
        return studentRepository.findByAgeBetween(max, min);
    }

    public String findByName(Long id) {
        Student s = studentRepository.findStudentById(id);
        if (s == null) {
            return null;
        }
        return s.getFaculty();
    }

    public Collection<Student> getFiveStudents() {
        return studentRepository.get5Students();
    }
    public String getAllStudents() {
        String count = " " + studentRepository.getAllStudents() + " ";
        return "количество студентов в школе: " + count;
    }

    public String getAvgAge() {
        String age = " " + studentRepository.getAvgAge() + " ";
        return "средний возраст студентов в школе: " + age;
    }
}
