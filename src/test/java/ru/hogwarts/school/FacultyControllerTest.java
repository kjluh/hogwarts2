package ru.hogwarts.school;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.controller.AvatarController;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.servise.FacultyService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = FacultyController.class)
public class FacultyControllerTest {

    @SpyBean
    private FacultyService facultyService;
    @MockBean
    private FacultyRepository facultyRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    JSONObject facultyObject = new JSONObject();
    Faculty faculty = new Faculty();
    Faculty faculty1 = new Faculty();

    Student student = new Student();
    List list = List.of(faculty, faculty1);

    @BeforeEach
    public void setUp() throws Exception {
        final Long id = 1L;
        final String name = "имя";
        final String color = "цвет";

        facultyObject.put("id", id);
        facultyObject.put("name", name);
        facultyObject.put("color", color);

        faculty.setId(id);
        faculty.setName(name);
        faculty.setColor(color);

        faculty1.setId(2L);
        faculty1.setName("name");
        faculty1.setColor("color");

//        student.setFaculty(faculty);
        student.setId(id);
        student.setName("aza");

        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);
        when(facultyRepository.findById(eq(id))).thenReturn(Optional.of(faculty));
        when(facultyRepository.findFacultyByNameIgnoreCase(any(String.class))).thenReturn(faculty);
        when(facultyRepository.findAll()).thenReturn(list);
        when(facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(any(String.class), any(String.class))).thenReturn(faculty);
        when(facultyRepository.findFacultyByNameIgnoreCase(any(String.class))).thenReturn(faculty);
    }

    @Test
    void createFaculty() throws Exception {
        final Long id = 1L;
        final String name = "имя";
        final String color = "цвет";
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    void getFaculty() throws Exception {
        final Long id = 1L;
        final String name = "имя";
        final String color = "цвет";
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/" + id)
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    void getFacultyByColor() throws Exception {
        final String color = "цвет";
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/all-faculty/" + color)
                        .queryParam("color", color)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(faculty))));
    }

    @Test
    void findByNameIgnoreCaseOrColorIgnoreCase() throws Exception {
        final Long id = 1L;
        final String name = "имя";
        final String color = "цвет";
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/findFaculty")
                        .queryParam("name", name)
                        .queryParam("color", color)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    void findByName() throws Exception {
        faculty.setStudentCollection(student);
        final String name = "имя";
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/findAll-students-by-faculty")
                        .queryParam("name", name)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(faculty.getStudentCollection())));

    }

    //update
    @Test
    void updateFaculty() throws Exception {
        final Long id = 1L;
        final String name = "имя";
        final String color = "цвет";
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }
// delete

    @Test
    void delete() throws Exception {
        final Long id = 1L;
        final String name = "имя";
        final String color = "цвет";
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculty/" + id)
                        .queryParam("id", String.valueOf(id))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }
}
