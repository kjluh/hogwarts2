package ru.hogwarts.school.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.servise.AvatarService;
import ru.hogwarts.school.servise.StudentService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;
    private final AvatarService avatarService;

    public StudentController(StudentService studentService, AvatarService avatarService) {
        this.studentService = studentService;
        this.avatarService = avatarService;
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

    @GetMapping(value = "/{id}/avatar-from-db")
    public ResponseEntity<byte[]> downloadAvatar(@PathVariable Long id) {
        Avatar avatar = avatarService.findAvatar(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        headers.setContentLength(avatar.getData().length);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(avatar.getData());
    }

    @GetMapping(value = "/{id}/avatar-from-file")
    public void downloadAvatar(@PathVariable Long id, HttpServletResponse response) throws IOException {
        Avatar avatar = avatarService.findAvatar(id);
        Path path = Path.of(avatar.getFilePath());
        try (
                InputStream is = Files.newInputStream(path);
                OutputStream os = response.getOutputStream();) {
                response.setStatus(200);
                response.setContentType(avatar.getMediaType());
                response.setContentLength((int) avatar.getFileSize());
                is.transferTo(os);
        }
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @PostMapping(value = "/{id}/cover", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadCover(@PathVariable Long id, @RequestParam MultipartFile avatar) throws IOException {
        avatarService.uploadCover(id, avatar);
        return ResponseEntity.ok().build();
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
