package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.servise.StudentService;

import java.util.Collection;
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
        Student example = studentService.getStudent(id);
        if (example != null) {
            return ResponseEntity.ok(studentService.getStudent(id));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("allStudents/{age}")
    public List<Student> getStudentByAge(@PathVariable int age) {
        return studentService.getStudentByAge(age);
    }

    @GetMapping("studentByAge")
    public ResponseEntity<Collection<Student>> findAllByAgeBetween(@RequestParam int max, @RequestParam int min) {
        Collection<Student> example = studentService.findAllByAgeBetween(max, min);
        if (example != null) {
            return ResponseEntity.ok(example);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("studentFaculty")
    public ResponseEntity<String> findByName(@RequestParam Long id) {
        String f = studentService.findByName(id);
        if (f != null) {
            return ResponseEntity.ok(f);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @PutMapping
    public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
        Student example = studentService.getStudent(student.getId());
        if (example != null) {
            return ResponseEntity.ok(studentService.updateStudent(student));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable Long id) {
        Student example = studentService.getStudent(id);
        if (example != null) {
            studentService.deleteStudent(id);
            return ResponseEntity.ok(example);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
