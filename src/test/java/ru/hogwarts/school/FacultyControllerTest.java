package ru.hogwarts.school;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.servlet.MockMvc;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.servise.FacultyService;

@WebMvcTest
public class FacultyControllerTest {

    @InjectMocks
    private FacultyController facultyController;

    @SpyBean
    private FacultyService facultyService;

    @MockBean
    private FacultyRepository facultyRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getFaculty() throws Exception{

    }
    @Test
    void getFacultyByColor() throws Exception{

    }

    @Test
    void findByNameIgnoreCaseOrColorIgnoreCase() throws Exception{

    }
    @Test
    void findByName() throws Exception{

    }
    @Test
    void createFaculty() throws Exception{

    }
    @Test
    void updateFaculty() throws Exception{

    }@Test
    void deleteFaculty() throws Exception{

    }
}
