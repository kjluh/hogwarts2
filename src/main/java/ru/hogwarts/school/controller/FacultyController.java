package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.servise.FacultyService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    public final FacultyService facultyService;

    public FacultyController(FacultyService facultyService)  {
        this.facultyService = facultyService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> getFaculty(@PathVariable Long id) {
        Faculty example = facultyService.getFaculty(id);
        if (example != null) {
            return ResponseEntity.ok(example);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("all-faculty/{color}")
    public List<Faculty> getFacultyByColor(@PathVariable String color) {
        return facultyService.getFacultyByColor(color);
    }

    @GetMapping("findFaculty")
    public ResponseEntity<Faculty> findByNameIgnoreCaseOrColorIgnoreCase(@RequestParam String name, @RequestParam String color) {
        Faculty example = facultyService.findByNameIgnoreCaseOrColorIgnoreCase(name, color);
        if (example != null) {
            return ResponseEntity.ok(example);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("findAll-students-by-faculty")
    public Collection<Student> findByName(@RequestParam String name) {
        return facultyService.findByName(name);
    }

    @PostMapping
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        return facultyService.createFaculty(faculty);
    }

    @PutMapping
    public ResponseEntity<Faculty> updateFaculty(@RequestBody Faculty faculty) {
        Faculty example = facultyService.getFaculty(faculty.getId());
        if (example != null) {
            return ResponseEntity.ok(facultyService.updateFaculty(faculty));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Faculty> deleteFaculty(@PathVariable Long id) {
        Faculty example = facultyService.getFaculty(id);
        if (example != null) {
            facultyService.deleteFaculty(id);
            return ResponseEntity.ok(example);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
