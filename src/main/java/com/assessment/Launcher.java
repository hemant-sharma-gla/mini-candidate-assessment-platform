package com.assessment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for the Spring Boot application.
 * This class serves as the entry point for the application and contains the main method
 * that bootstraps the Spring Boot application.
 */
@SpringBootApplication
public class Launcher {

    /**
     * The main method that serves as the entry point for the Spring Boot application.
     * It initializes and starts the Spring application context.
     *
     * @param args Command line arguments passed to the application
     */
    public static void main(String[] args) {
        SpringApplication.run(Launcher.class, args);
    }

}
