package ru.hogwarts.school.servise;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import java.util.HashMap;
import java.util.List;

@Service
public class FacultyService {
    private final HashMap<Long, Faculty> facultyService = new HashMap<>();
    private Long count = 0L;

    public Faculty getFaculty(Long id) {
        if (facultyService.containsKey(id)) {
            return facultyService.get(id);
        }
        return null;
    }

    public List<Faculty> getFacultyByColor(String color) {
        return facultyService.values().stream().filter(e -> e.getColor().equals(color)).toList();
    }

    public Faculty createFaculty(Faculty faculty) {
        faculty.setId(++count);
        return facultyService.put(faculty.getId(), faculty);
    }

    public Faculty updateFaculty(Faculty faculty) {
        if (facultyService.containsKey(faculty.getId())) {
            return facultyService.put(faculty.getId(), faculty);
        }
        return null;
    }

    public Faculty deleteFaculty(Long id) {
        if (facultyService.containsKey(id)) {
            return facultyService.remove(id);
        }
        return null;
    }
}
