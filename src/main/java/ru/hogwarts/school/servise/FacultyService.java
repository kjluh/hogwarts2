package ru.hogwarts.school.servise;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;

import java.util.Collection;
import java.util.List;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;

    Logger logger = LoggerFactory.getLogger(FacultyService.class);

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty getFaculty(Long id) {
        logger.debug("Вызван метод getFaculty по id {}",id);
        return facultyRepository.findById(id).orElse(null);
    }

    public List<Faculty> getFacultyByColor(String color) {
        logger.debug("Вызван метод getFacultyByColor");
        return facultyRepository.findAll().stream().filter(e -> e.getColor().equals(color)).toList();
    }

    public Faculty createFaculty(Faculty faculty) {
        logger.debug("Вызван метод createFaculty");
        return facultyRepository.save(faculty);
    }

    public Faculty updateFaculty(Faculty faculty) {
        logger.debug("Вызван метод updateFaculty");
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(Long id) {
        logger.debug("Вызван метод deleteFaculty");
        facultyRepository.deleteById(id);
    }

    public Faculty findByNameIgnoreCaseOrColorIgnoreCase(String name, String color) {
        logger.debug("Вызван метод findByNameIgnoreCaseOrColorIgnoreCase");
        return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }

    public Collection<Student> findByName(String name) {
        logger.debug("Вызван метод findByName");
        Faculty f = facultyRepository.findFacultyByNameIgnoreCase(name);
        return f.getStudentCollection();
    }
}
