package ru.hogwarts.school;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Student;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class Hogwarts2ApplicationTests {

    @LocalServerPort
    private int port;
    @Autowired
    StudentController studentController;

    @Autowired
    TestRestTemplate testRestTemplate;
    Student s = new Student();

    @Test
    void contextLoads() throws Exception {
        Assertions.assertThat(studentController).isNotNull();
    }

    @Test
    void createStudent() throws Exception {
        s.setName("ivan");
        s.setAge(22);
        Assertions
                .assertThat(this.testRestTemplate.postForObject("http://localhost:" + port + "/student", s, String.class))
                .isNotNull();
    }

    @Test
    void getStudent() throws Exception {
        s.setName("Толя");
        s.setAge(23);
        this.testRestTemplate.postForObject("http://localhost:" + port + "/student", s, String.class);
        Long id = s.getId();
        Assertions
                .assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/student/" + id, String.class))
                .isNotNull();
    }

    @Test
    void getStudentByAge() throws Exception {
        s.setName("Толя");
        s.setAge(23);
        this.testRestTemplate.postForObject("http://localhost:" + port + "/student", s, String.class);
        int age = s.getAge();
        Assertions.assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/student/allStudents" + age, String.class))
                .isNotNull();
    }

    @Test
    void findAllByAgeBetween() throws Exception {
        Assertions.assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/student/studentByAge", String.class))
                .isNotNull();
    }

    @Test
    void findByName() throws Exception {
        s.setName("Толя");
        s.setAge(23);
        this.testRestTemplate.postForObject("http://localhost:" + port + "/student", s, String.class);
        Long id = s.getId();
        Assertions.
                assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/student/studentFaculty" + id, String.class))
                .isNotNull();
    }

    //    // НЕ ПОНИМАЮ КАК ТЕСТИТЬ МЕТОД ПОЛУЧЕНИЯ КАРТИНКИ, КАКОЙ КЛАСС ВЫБИРАТЬ.
////    @Test
////    void downloadAvatar() throws Exception{
////        Assertions.assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/student/2/avatar-from-db", MediaType.MULTIPART_FORM_DATA_VALUE.class))
////                .isNotNull();
////    }
//
//
    @Test
    void updateStudent() throws Exception {
        s.setName("Толя");
        s.setAge(23);
        this.testRestTemplate.postForObject("http://localhost:" + port + "/student", s, String.class);
        Long id = s.getId();

        Assertions
                .assertThat(this.testRestTemplate.postForObject("http://localhost:" + port + "/student" + id, s, String.class))
                .isNotNull();
    }

    @Test
    void deleteStudent() throws Exception {
        s.setName("Толя");
        s.setAge(23);
        this.testRestTemplate.put("http://localhost:" + port + "/student", s, String.class);
        Long id = s.getId();
        this.testRestTemplate.delete("http://localhost:" + port + "/student" + id,s,String.class);
        Assertions
                .assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/student" + id, String.class))
                .isEqualTo(HttpStatus.NOT_FOUND);
    }
}