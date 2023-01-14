package ru.hogwarts.school.servise;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.HashMap;
import java.util.List;

@Service
public class StudentService {
    private final HashMap<Long, Student> studentService = new HashMap<>();
    private Long count = 0L;

    public Student getStudent(Long id) {
        if (studentService.containsKey(id)) {
            return studentService.get(id);
        }
        return null;
    }

    public List<Student> getStudentByAge(int age) {
        return studentService.values().stream().filter(e->e.getAge()==age).toList();
    }

    public Student createStudent(Student student) {
        student.setId(++count);
        return studentService.put(student.getId(), student);
    }

    public Student updateStudent(Student student) {
        if (studentService.containsKey(student.getId())) {
            return studentService.put(student.getId(), student);
        }
        return null;
    }

    public Student deleteStudent(Long id) {
        if (studentService.containsKey(id)) {
            return studentService.remove(id);
        }
        return null;
    }
}
