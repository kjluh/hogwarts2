package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.servise.FacultyService;

import java.util.List;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    public final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> getFaculty(@PathVariable Long id) {
        Faculty example = facultyService.getFaculty(id);
        if (example != null) {
            return ResponseEntity.ok(example);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("allfaculty/{color}")
    public List<Faculty> getFacultyByColor(@PathVariable String color) {
        return facultyService.getFacultyByColor(color);
    }

    @PostMapping
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        return facultyService.createFaculty(faculty);
    }

    @PutMapping
    public ResponseEntity<Faculty> updateFaculty(@RequestBody Faculty faculty) {
        Faculty example = facultyService.getFaculty(faculty.getId());
        if (example != null) {
            facultyService.updateFaculty(faculty);
            return ResponseEntity.ok(faculty);
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Faculty> deleteFaculty(@PathVariable Long id) {
        Faculty example = facultyService.getFaculty(id);
        if (example != null) {
            return ResponseEntity.ok(facultyService.deleteFaculty(id));
        }
        return ResponseEntity.badRequest().build();
    }
}
