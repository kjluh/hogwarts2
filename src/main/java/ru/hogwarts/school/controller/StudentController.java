package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.servise.StudentService;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        if (studentService.getStudent(id) != null) {
            return ResponseEntity.ok(studentService.getStudent(id));
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("allStudents/{age}")
    public List<Student> getStudentByAge(@PathVariable int age) {
        return studentService.getStudentByAge(age);
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @PutMapping
    public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
        Student example = studentService.getStudent(student.getId());
        if (example != null) {
            studentService.updateStudent(student);
            return ResponseEntity.ok(student);
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable Long id) {
        Student example = studentService.getStudent(id);
        if (example != null) {
            return ResponseEntity.ok(studentService.deleteStudent(id));
        }
        return ResponseEntity.badRequest().build();
    }
}
