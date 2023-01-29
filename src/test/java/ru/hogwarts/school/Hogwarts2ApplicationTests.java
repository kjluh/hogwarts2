package ru.hogwarts.school;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class Hogwarts2ApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    TestRestTemplate testRestTemplate;
    Student s = new Student();

    @BeforeEach
    void setUp() {
        s.setName("Толя");
        s.setAge(23);
    }

    @Test
    void contextLoads() throws Exception {
        Assertions.assertThat(studentController).isNotNull();
    }

    @Test
    void createStudent() throws Exception {
        Assertions
                .assertThat(this.testRestTemplate.postForObject("http://localhost:" + port + "/student", s, Student.class))
                .isNotNull();
    }

    @Test
    void getStudent() throws Exception {
        s.setId(29l);
        Assertions
                .assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/student/29", Student.class))
                .isEqualTo(s)
        ;
    }

    @Test
    void getStudentByAge() throws Exception {
        Assertions.assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/student/allStudents", String.class))
                .isNotNull();
    }

    @Test
    void findAllByAgeBetween() throws Exception {
        Assertions.assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/student/studentByAge", Student.class))
                .isNotNull();
    }

    @Test
    void findByName() throws Exception {
        Assertions.
                assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/student/studentFaculty", Faculty.class))
                .isNotNull();
    }

    // НЕ ПОНИМАЮ КАК ТЕСТИТЬ МЕТОД ПОЛУЧЕНИЯ КАРТИНКИ, КАКОЙ КЛАСС ВЫБИРАТЬ.
//    @Test
//    void downloadAvatar() throws Exception{
//        Assertions.assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/student/2/avatar-from-db", MediaType.MULTIPART_FORM_DATA_VALUE.class))
//                .isNotNull();
//    }


    @Test
    void updateStudent() throws Exception {
        s.setName("anatoly");
        Assertions
                .assertThat(this.testRestTemplate.postForObject("http://localhost:" + port + "/student", s, Student.class))
                .isEqualTo(s);
    }

    @Test
    void deleteStudent() throws Exception {
//        s.setName("anatoly");
        testRestTemplate.delete("http://localhost:" + port + "/student/29");
        Assertions
                .assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/student/" + s.getId(), Student.class))
                .isNull();
    }
}
