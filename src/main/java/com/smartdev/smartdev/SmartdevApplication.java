package com.smartdev.smartdev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main class for the Smartdev Spring Boot application.
 * <p>
 * This class serves as the entry point for the Spring Boot application.
 * The main method uses Spring Boot’s SpringApplication.run() method to launch the application.
 */
@SpringBootApplication
@EnableScheduling
public class SmartdevApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartdevApplication.class, args);
    }

}
