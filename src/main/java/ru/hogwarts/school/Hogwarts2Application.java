package ru.hogwarts.school;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class Hogwarts2Application {

    public static void main(String[] args) {
        SpringApplication.run(Hogwarts2Application.class, args);
    }

}
