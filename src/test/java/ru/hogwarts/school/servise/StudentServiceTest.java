package ru.hogwarts.school.servise;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {
    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;
    Student example = new Student();

    @BeforeEach
    public void setUp() {
        example.setId(10L);
        example.setAge(50);
        example.setName("Max");
    }

    @Test
    public void getStudentByID() {
        Mockito.when(studentRepository.findById(1L)).thenReturn(Optional.ofNullable(example));
        Assertions.assertEquals(studentService.getStudent(1L),(example));
    }
    @Test
    public void getStudentByIDEqualsNull() {
        Mockito.when(studentRepository.findById(1L)).thenReturn(Optional.ofNullable(null));
        Assertions.assertEquals(studentService.getStudent(1L),(null));
    }


}
