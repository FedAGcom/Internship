package com.fedag.internship;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class InternshipApplication {
    public static void main(String[] args) {
        SpringApplication.run(InternshipApplication.class, args);
    }
}
