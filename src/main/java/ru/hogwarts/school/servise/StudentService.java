package ru.hogwarts.school.servise;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Collection;
import java.util.List;

@Service
@Profile("!test")
public class StudentService {
    private final StudentRepository studentRepository;

    Logger logger = LoggerFactory.getLogger(StudentService.class);

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student getStudent(Long id) {
        logger.debug("Вызван метод getStudent c id {}", id);
        return studentRepository.findById(id).orElse(null);
    }

    public List<Student> getStudentByAge(int age) {
        logger.debug("Вызван метод getStudentByAge c возрастом {}", age);
        return studentRepository.findAll().stream().filter(e -> e.getAge() == age).toList();
    }

    public Student createStudent(Student student) {
        logger.debug("Вызван метод createStudent");
        return studentRepository.save(student);
    }

    public Student updateStudent(Student student) {
        logger.debug("Вызван метод updateStudent");
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        logger.debug("Вызван метод deleteStudent c id {}", id);
        studentRepository.deleteById(id);
    }

    public Collection<Student> findAllByAgeBetween(int max, int min) {
        logger.debug("Вызван метод findAllByAgeBetween ");
        return studentRepository.findByAgeBetween(max, min);
    }

    public String findByName(Long id) {
        logger.debug("Вызван метод findByName ");
        Student s = studentRepository.findStudentById(id);
        if (s == null) {
            return null;
        }
        return s.getFaculty();
    }

    public Collection<Student> getFiveStudents() {
        logger.debug("Вызван метод getFiveStudents ");
        return studentRepository.get5Students();
    }

    public String getAllStudents() {
        logger.debug("Вызван метод getAllStudents ");
        String count = " " + studentRepository.getAllStudents() + " ";
        return "количество студентов в школе: " + count;
    }

    public String getAvgAge() {
        logger.debug("Вызван метод getAvgAge ");
        String age = " " + studentRepository.getAvgAge() + " ";
        return "средний возраст студентов в школе: " + age;
    }

    public List<String> searchForNameByLetter(String letter) {
        return studentRepository.
                findAll().stream().map(e -> e.getName().toUpperCase()).
                filter(e -> e.charAt(0) == letter.toUpperCase().charAt(0)).toList();
    }

    public double middleAge() {
        return studentRepository.findAll().stream().
                mapToInt(Student::getAge).average().orElseThrow();
    }

    public void NameIn3Thread() {
        String[] nameStudent = helpmetod2();

        helpmetod(nameStudent[0]);
        System.out.println("поток 1");
        helpmetod(nameStudent[1]);
        System.out.println("поток 1");

        new Thread(() -> {
            helpmetod(nameStudent[2]);
            System.out.println("поток 2");
            helpmetod(nameStudent[3]);
            System.out.println("поток 2");
        }).start();

        new Thread(() -> {
            helpmetod(nameStudent[4]);
            System.out.println("поток 3");
            helpmetod(nameStudent[5]);
            System.out.println("поток 3");
        }).start();
//        System.out.println(nameStudent[0]);
//        new Thread(() ->{
//                System.out.println(nameStudent[2]);
//                System.out.println(nameStudent[3]);
//        }).start();
//        new Thread(() ->{
//            System.out.println(nameStudent[4]);
//            System.out.println(nameStudent[5]);
//        }).start();
//        System.out.println(nameStudent[1]);
    }

    private void helpmetod(String string) {
        synchronized (flag) {
            System.out.println(string);
        }
    }

    private String[] helpmetod2() {
        return studentRepository.findAll().stream().map(Student::getName).toList().toArray(new String[7]);
    }

    private Object flag = new Object();

    public void NameIn3Thread2() {
        String[] nameStudent = helpmetod2();
        helpmetod(nameStudent[0]);
        helpmetod(nameStudent[1]);

        new Thread(() -> {
            helpmetod(nameStudent[2]);
            helpmetod(nameStudent[3]);
        }).start();

        new Thread(() -> {
            helpmetod(nameStudent[4]);
            helpmetod(nameStudent[5]);
        }).start();
    }
}
