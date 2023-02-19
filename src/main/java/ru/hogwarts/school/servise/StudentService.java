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
        String[] nameStudent = helpMetod2();

        Thread thread2 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " " + nameStudent[2]);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + " " + nameStudent[3]);
        });

        Thread thread3 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " " + nameStudent[4]);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + " " + nameStudent[5]);
        });
        thread2.start();
        System.out.println(Thread.currentThread().getName() + " " + nameStudent[0]);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(Thread.currentThread().getName() + " " + nameStudent[1]);
        thread3.start();
    }

    private String[] helpMetod2() {
        return studentRepository.findAll().stream().map(Student::getName).limit(6).toList().toArray(new String[0]);
    }

    private final Object flag = new Object();
    private int count = 0;

    public void NameIn3Thread2() {
        String[] nameStudent2 = helpMetod2();
        getName(nameStudent2[0]);
        getName(nameStudent2[1]);
        new Thread(() -> {
            getName(nameStudent2[2]);
            getName(nameStudent2[3]);
        }).start();

        new Thread(() -> {
            getName(nameStudent2[4]);
            getName(nameStudent2[5]);
        }).start();
    }

    private void getName(String s) {
        synchronized (flag) {
            System.out.println(Thread.currentThread().getName() + " " + s);
        }
    }
}
